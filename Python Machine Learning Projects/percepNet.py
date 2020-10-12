""" Written by: Riley Everett    Student ID: 973838691 """

#  import needed libraries
import numpy as np
import matplotlib.pyplot as plt
import pandas as pd
import seaborn as sn

#  import training and validation data from file
file_data = np.loadtxt('mnist_train.csv', dtype=str, delimiter=',')
validation_data = np.loadtxt('mnist_validation.csv', dtype=str, delimiter=',')

#  shuffle training data and break into labels and image data
np.random.shuffle(file_data)
training_labels = np.asarray(file_data[:, 0:1], dtype='float')
training_data = np.asarray(file_data[:, 1:], dtype='float')

#  shuffle validation data and break into labels and image data
np.random.shuffle(validation_data)
validation_labels = np.asarray(validation_data[:, 0:1], dtype='float')
validation_data = np.asarray(validation_data[:, 1:], dtype='float')

#  get count of training examples
training_count = len(file_data[:, 0])
validation_count = len(validation_data[:, 0])

#  make an array to divide training data by
div_by = np.full((training_count, 784), 255)

#  divide each data value in the training data by 255 so they are between 0 - 1
training_data = np.divide(training_data, div_by)

#  generate random weights and bias for network
weights = np.random.rand(10, 784) - 0.5
weight_copy = np.copy(weights)
bias = np.random.rand(10, 1)

#  set learning rate and number of epochs
learning_rate = 0.1
epochs = 50

#  prediction accuracy lists
prediction_accuracy = np.zeros(epochs)
validation_accuracy = np.zeros(epochs)

#  loop through 50 epochs
for epoch in range(epochs):
    #  lists to store label targets and predictions for accuracy calculation
    test_prediction_list = []
    test_target_list = []
    val_prediction_list = []
    val_target_list = []

    #  values for tracking accuracy
    test_correct = 0
    val_correct = 0

    #  run through all training examples
    for row in range(training_count):
        #  array for storing prediction values
        cur_predictions = []
        #  create target and output matrix to compare prediction to actual label
        target = int(training_labels[row])
        test_target_list.append(target)

        label_matrix = np.zeros(10)
        label_matrix[target] = 1
        output_matrix = np.zeros(10)
        for percep in range(10):
            #  calculate prediction using perceptron algorithm
            percep_output = np.dot(training_data[row, :], weights[percep, :]) + (1 * bias[percep])
            if percep_output <= 0:
                output_matrix[percep] = 0
            else:
                output_matrix[percep] = 1

            #  collect prediction values
            cur_predictions.append(percep_output)

            if epoch != 0:
                #  get error value
                error = float(label_matrix[percep] - output_matrix[percep])

                #  adjust bias and weights based on perceptron output if needed
                if error != 0:
                    bias[percep] += (learning_rate * error * 1)
                    for i in range(784):
                        weights[percep, i] += (learning_rate * error * float(training_data[row, i]))

        #  add the largest prediction value to an array of predictions
        test_prediction_list.append(np.argmax(np.array(cur_predictions)))

    #  check for correctness in training predictions
    for index in range(training_count):
        if test_prediction_list[index] == int(test_target_list[index]):
            test_correct += 1

    #  run through all validation examples
    for row in range(validation_count):
        #  array for storing prediction values
        cur_val_predictions = []
        #  create target and output matrix to compare prediction to actual label
        val_target = int(validation_labels[row])
        val_target_list.append(val_target)
        for percep in range(10):
            #  calculate prediction using perceptron algorithm
            percep_output = np.dot(validation_data[row, :], weights[percep, :]) + (1 * bias[percep])

            #  collect prediction values
            cur_val_predictions.append(percep_output)

            #  add the largest prediction value to an array of predictions
        val_prediction_list.append(np.argmax(np.array(cur_val_predictions)))

    for index in range(validation_count):
        if val_prediction_list[index] == int(val_target_list[index]):
            val_correct += 1

    #  calculate and print accuracy
    prediction_accuracy[epoch] = (test_correct / training_count) * 100
    validation_accuracy[epoch] = (val_correct / validation_count) * 100
    print('Epoch {}: '.format(epoch))
    print('training accuracy = {0:.2f}% '.format(prediction_accuracy[epoch]))
    print('correct is {} / {}\n'.format(test_correct, training_count))
    print('validation accuracy = {0:.2f}% '.format(validation_accuracy[epoch]))
    print('correct is {} / {}\n'.format(val_correct, validation_count))

    if epoch == epochs - 1:
        #  create and plot confusion matrix
        cm = np.zeros((10, 10))

        for i in range(validation_count):
            cm[val_target_list[i], val_prediction_list[i]] += 1
        df_cm = pd.DataFrame(cm, index=[i for i in "0123456789"],
                             columns=[i for i in "0123456789"])
        plt.figure(figsize=(10, 7))
        sn.heatmap(df_cm, annot=True)
        #  plt.savefig('lr_0.1_cm_fig.png')

#  plot and save accuarcy graphs
test_accuracy = []
val_accuracy = []

for i in range(epochs):
    test_accuracy.append(prediction_accuracy[i])
    val_accuracy.append(validation_accuracy[i])

x = np.arange(epochs)
acc_fig = plt.figure()
plt.xlabel('Epochs', fontsize=12)
plt.ylabel('Accuracy', fontsize=12)
plt.plot(x, test_accuracy, linewidth=2)
plt.plot(x, val_accuracy, linewidth=2)
#  acc_fig.savefig('lr_0.1_acc_fig.png')
