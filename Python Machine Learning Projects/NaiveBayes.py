# Written by Riley Everett
# Student ID: 973838691

import sys
import numpy as np

#  constants
min_std = 0.01

#  get file name from command line
training_fn = sys.argv[1]
test_fn = sys.argv[2]

#  get file data
training_file_data = np.loadtxt(training_fn)
test_file_data = np.loadtxt(test_fn)

#  split files into data and labels
training_data = np.asarray(training_file_data[:, :-1], dtype=float)
training_labels = np.asarray(training_file_data[:, -1], dtype=float)

test_data = np.asarray(test_file_data[:, :-1], dtype=float)
test_labels = np.asarray(test_file_data[:, -1], dtype=float)

#  get stats about data to help with calculations
label_num = len(np.unique(training_labels))
label_types = np.unique(training_labels)
col_num = len(training_data[0, :])
row_num = len(training_data[:, 0])
test_row_num = len(test_data[:, 0])

#  create matrices to hold mean, standard deviation, class probabilities, and predictions
mean_arr = np.zeros((label_num, col_num))
std_arr = np.zeros((label_num, col_num))
class_probability = np.zeros(label_num)
predictions = np.zeros((test_row_num, 2))
predictions_accuracy = np.zeros(test_row_num)

#  calculate class probabilities
for i in range(label_num):
    cnt = 0
    for j in range(row_num):
        if training_labels[j] == label_types[i]:
            cnt += 1
    class_probability[i] = cnt / row_num

#  process training data
for i in range(label_num):
    #  get mean and std for each label
    for j in range(col_num):
        #  for holding column values for current label
        temp_arr = []
        for x in range(row_num):
            #  find all value that match current label in current row
            if training_labels[x] == label_types[i]:
                temp_arr.append(training_data[x, j])
        #  calculate  ans store mean and stdev
        cur_mean = np.mean(temp_arr)
        cur_std = np.std(temp_arr)
        if cur_std < min_std:
            cur_std = min_std
        mean_arr[i, j] = cur_mean
        std_arr[i, j] = cur_std

#  print training data
for label in range(label_num):
    for col in range(col_num):
        print('Class: {}, attribute {}, mean = {:.2f}, std = {:.2f}'
              .format(int(label_types[label]), col + 1, mean_arr[label, col], std_arr[label, col]))
    print('')  # add line break

#  make predictions for test data
#  get prediction for all 10 classes using equation
for row in range(test_row_num):
    label_values = []
    for label in range(label_num):
        temp_arr = []
        for col in range(col_num):
            #  use Gaussian Naive Bayes equation with current column and class
            x = test_data[row, col]
            mean = mean_arr[label, col]
            stdev = std_arr[label, col]

            exponent = np.exp(-((x - mean) ** 2) / (2 * stdev ** 2))
            result = (1 / (np.sqrt(2 * np.pi) * stdev)) * exponent
            temp_arr.append(result)

        #  calculate the product of all col value and class probability
        z = class_probability[label] * np.prod(temp_arr)
        label_values.append(z)

    #  store largest activation as prediction
    predict_index = np.argmax(label_values)
    ties = -1
    value = label_values[int(predict_index)]
    for i in label_values:
        if i == value:
            ties += 1
    predictions[row, 0] = label_types[predict_index]
    predictions[row, 1] = ties

#  print testing accuracy
for i in range(test_row_num):
    #  check if prediction matches label
    label_val = int(test_labels[i])
    if predictions[i, 0] == label_val:
        tf_val = True
    else:
        tf_val = False

    #  calculate accuracy value
    pred_acc = predictions[i, 1]
    if pred_acc > 0:
        if tf_val:
            pred_acc = 1 / (pred_acc + 1)
            predictions[i, 1] = pred_acc
        else:
            pred_acc = 0
            predictions[i, 1] = pred_acc
    else:
        if tf_val:
            pred_acc = 1
            predictions[i, 1] = pred_acc
        else:
            pred_acc = 0
            predictions[i, 1] = pred_acc

    #  get prediction value
    pred = int(predictions[i, 0])

    # get predicted class index
    pred_index = 0
    for label in range(label_num):
        if label_types[label] == pred:
            pred_index = label

    #  print data for user
    print('ID: {}, predicted = {}, probability = {:.4f}, true = {}, accuracy = {:.4f}'
          .format(i + 1, pred, class_probability[pred_index], label_val, pred_acc))

#  calculate and print accuracy
acc_val = np.mean(predictions[:, 1])
print('\nClassification Accuracy: {0:.4f}%'.format(acc_val * 100))
