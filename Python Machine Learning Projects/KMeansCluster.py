# Written by Riley Everett
# Student ID: 973838691

"""
This program uses k-means clustering to train a group of clusters points on a data set and then test them
using data not used in the training process. Using the constant values at the top of the file you can adjust
the number of cluster points (k_val) the program uses, the number of attributes (att_val) in each line of data,
and the number of loops (train_loop) the program will execute each time starting over with random starting values
before selecting a grouping of clusters with a minimum mean squared error to test with the validation data set.
"""

import numpy as np
import seaborn as sn
import pandas as pd
import math
from PIL import Image
from statistics import mode
from scipy.spatial import distance
from scipy.stats import entropy
import matplotlib.pyplot as plt

#  constant values
k_val = 10
att_val = 64
train_loops = 1

# read in data from files
training_file = np.loadtxt('optdigits/optdigits.train', delimiter=',')
test_file = np.loadtxt('optdigits/optdigits.test', delimiter=',')
np.seterr(divide='ignore', invalid='ignore')

#  split data into attributes and labels
training_data = training_file[:, :-1]
training_labels = training_file[:, -1]

test_data = test_file[:, :-1]
test_labels = test_file[:, -1]

#  get length of training and test data
train_length = len(training_data[:, 0])
test_length = len(test_data[:, 0])

#  calculate number of cluster combinations
k_comb = math.comb(k_val, 2)

#  array to store classifications
classifications = np.zeros(train_length, dtype=int)

#  storage for cluster centers and counts
cluster_points = np.zeros((k_val, att_val))
class_count = np.zeros(k_val, dtype=int)

#  storage for cluster centers across multiple loops
loop_clusters = []
for i in range(train_loops):
    loop_clusters.append(np.zeros((k_val, att_val)))

#  storage for cluster modes
cluster_modes = np.zeros(k_val)
loop_modes = []
for i in range(train_loops):
    loop_modes.append(np.zeros(k_val))

# storage for clusters classification
cluster_class = np.zeros(k_val)

#  storage for mean squared error, separation and entropy
iter_mse = np.zeros(train_loops)
mse_arr = np.zeros(k_val)
mse = 0

iter_mss = np.zeros(train_loops)
mss = 0

iter_entropy = np.zeros(train_loops)
entropy_arr = np.zeros(k_val)
mean_entropy = 0

for loop in range(train_loops):
    #  stopping value for finding cluster centers
    update_change = True
    #  counter for number of iterations
    iter_count = 0

    # get initial values for each of k clusters
    rand_vals = np.random.randint(low=0, high=train_length, size=k_val)
    for i in range(k_val):
        cluster_points[i, :] = np.copy(training_data[rand_vals[i], :])

    #  loop while cluster centers still change
    while update_change:
        #  reset class count array and mean entropy to zero
        class_count.fill(0)
        mean_entropy = 0
        #  process and classify training data
        for row in range(train_length):
            cluster_arr = []
            x = training_data[row, :]
            #  classify the current row of data
            for k in range(k_val):
                y = cluster_points[k, :]
                cluster_arr.append(distance.euclidean(x, y))
            #  store classification and add to count of that cluster
            cl = int(np.argmin(np.array(cluster_arr)))
            classifications[row] = cl
            class_count[cl] += 1

        #  make list of arrays to update cluster centers
        update_rows = []
        update_labels = []
        for k in range(k_val):
            update_rows.append(np.empty((0, att_val)))
            update_labels.append(np.empty((0, 1)))

        # add all rows and labels of each cluster to their own arrays
        for row in range(train_length):
            #  get classification for rows data
            classif = classifications[row]
            #  get values in this row and make them 2d
            values = training_data[row, :]
            values = np.reshape(values, (-1, att_val))
            #  get label in this row and make it 2d
            label = training_labels[row]
            label = np.reshape(label, (-1, 1))
            #  get reference to lists for appending
            data_arr = update_rows[classif]
            label_arr = update_labels[classif]
            #  append data and label to lists
            update_rows[classif] = np.append(data_arr, values, axis=0)
            update_labels[classif] = np.append(label_arr, label, axis=0)

        #  update all cluster locations
        old_points = np.copy(cluster_points)
        for k in range(k_val):
            cluster = cluster_points[k]
            for att in range(att_val):
                cur_arr = update_rows[k]
                cur_col = cur_arr[:, att]
                new_val = np.sum(cur_col) / class_count[k]
                tmp = cluster[att]
                cluster[att] = new_val

        #  get the mode of each cluster
        for k in range(k_val):
            labels_val = np.ravel(update_labels[k])
            if len(labels_val) == 0:
                cluster_modes[k] = -1
            else:
                cluster_modes[k] = mode(labels_val)

        #  count number of iterations
        iter_count += 1

        #  calculate mse of each cluster
        for k in range(k_val):
            #  mse
            cur_cluster = cluster_points[k]
            cur_points = update_rows[k]
            cur_len = len(cur_points)
            temp = np.zeros(cur_len)
            for i in range(cur_len):
                x = cur_points[i, :]
                temp[i] = distance.euclidean(x, cur_cluster) ** 2
            numerator = np.sum(temp)
            mse_arr[k] = (numerator / cur_len)

        # calculate distance of each pair of cluster points for mss
        k_dist_vals = []
        for k in range(k_val):
            for i in range(k_val - (k + 1)):
                index = (i + 1) + k
                dist = distance.euclidean(cluster_points[k], cluster_points[index]) ** 2
                k_dist_vals.append(dist)

        #  calculate entropy values for each cluster
        for k in range(k_val):
            ent_val = entropy(update_labels[k], base=2)
            if math.isnan(ent_val):
                ent_val = 0
            entropy_arr[k] = ent_val

        #  calculate average mse, mss, and entropy
        mse = np.sum(mse_arr) / k_val
        denominator = (k_val * (k_val - 1)) / 2
        mss = np.sum(k_dist_vals) / denominator
        for k in range(k_val):
            num_points = len(update_rows[k])
            num_cluster = (num_points / train_length)
            mean_entropy += (num_cluster * entropy_arr[k])
        mean_entropy = mean_entropy / k_val
        #  check if cluster points have changed
        update_change = not (np.array_equal(cluster_points, old_points))

    #  save clusters, modes, mse, mss, and entropy for this loop
    print('clusters converged in {} iterations'.format(iter_count))
    loop_clusters[loop] = np.copy(cluster_points)
    loop_modes[loop] = np.copy(cluster_modes)
    iter_mse[loop] = mse
    iter_mss[loop] = mss
    iter_entropy[loop] = mean_entropy

#  get min mse from loops
min_loop = int(np.argmin(iter_mse))
print('run with min mse is {} with a mse of {:.2f}, mss of {:.2f}, and entropy of {:.2f}'
      .format(min_loop, iter_mse[min_loop], iter_mss[min_loop], iter_entropy[min_loop]))

test_clusters = np.copy(loop_clusters[min_loop])
test_modes = np.copy(loop_modes[min_loop])
test_classifications = np.zeros(test_length, dtype=int)

for row in range(test_length):
    # classify each row in the test data
    cluster_arr = []
    x = test_data[row, :]
    #  classify the current row of data
    for k in range(k_val):
        y = test_clusters[k, :]
        cluster_arr.append(distance.euclidean(x, y))
    #  store classification
    cl = int(np.argmin(np.array(cluster_arr)))
    test_classifications[row] = cl

#  get test data classification accuracy and create confusion matrix
accuracy = 0
cm = np.zeros((10, 10))

for row in range(test_length):
    classif = test_classifications[row]
    cluster_label = int(test_modes[classif])
    row_label = int(test_labels[row])
    cm[cluster_label, row_label] += 1
    if cluster_label == row_label:
        accuracy += 1

accuracy_total = (accuracy / test_length) * 100

df_cm = pd.DataFrame(cm, index=[i for i in "0123456789"], columns=[i for i in "0123456789"])
plt.figure(figsize=(10, 7))
sn.heatmap(df_cm, annot=True)
plt.savefig('k' + str(k_val) + '_HeatMap.png')

print('Test classification accuracy = {:.2f}%'.format(accuracy_total))

#  convert clusters attributes into grayscale images and save them to png files
new_size = (300, 300)
for k in range(k_val):
    arr = np.reshape(test_clusters[k], (8, 8))
    arr = (arr * 255).astype(np.uint8)
    img = Image.fromarray(arr, mode='L')
    img = img.resize(new_size, resample=Image.NEAREST)
    img.save('k' + str(k_val) + 'cluster' + str(k) + 'GS.png')

    print('Mode label for cluster {} is {}'.format(k, test_modes[k]))
