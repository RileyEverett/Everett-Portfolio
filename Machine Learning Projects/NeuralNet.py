""" Written by: Riley Everett    Student ID: 973838691 """

#  import needed libraries
import numpy as np
import matplotlib.pyplot as plt
import pandas as pd
import seaborn as sn
from datetime import datetime
from scipy.special import expit


def derivative(val):
    return val * (1.0 - val)


# get start time
start = datetime.now()

#  set constant values for experiment
learning_rate = 0.1
epochs = 50
hidden_units = 100
output_units = 10
input_units = 784
weight_gen_min = -0.05
weight_gen_max = 0.05
training_ex = 60000
exp2 = True
sub_div = 0.25


#  import training and validation data from file
file_data = np.loadtxt('mnist_train.csv', dtype=str, delimiter=',')
validation_data = np.loadtxt('mnist_validation.csv', dtype=str, delimiter=',')

#  shuffle training data and break into labels and image data
np.random.shuffle(file_data)
if not exp2:
    training_labels = np.asarray(file_data[:, 0:1], dtype='float')
    training_data = np.asarray(file_data[:, 1:], dtype='float')
else:
    num_rows = int(sub_div * training_ex)
    train_temp = np.zeros((num_rows, 785))
    row_count = 0
    counter_arr = np.zeros(10)
    limit = num_rows / 10
    for i in range(training_ex):
        if row_count == num_rows:
            break
        curr = file_data[i, :]
        if counter_arr[int(curr[0])] != limit:
            train_temp[row_count] = curr
            counter_arr[int(curr[0])] += 1
            row_count += 1

    training_labels = np.asarray(train_temp[:, 0:1], dtype='float')
    training_data = np.asarray(train_temp[:, 1:], dtype='float')

#  shuffle validation data and break into labels and image data
np.random.shuffle(validation_data)
validation_labels = np.asarray(validation_data[:, 0:1], dtype='float')
validation_data = np.asarray(validation_data[:, 1:], dtype='float')

#  get count of training examples
training_count = len(training_data[:, 0])
validation_count = len(validation_data[:, 0])

#  make an array to divide data by
div_by = np.full((training_count, input_units), 255)
val_div_by = np.full((validation_count, input_units), 255)

#  divide each data value in the data by 255 so they are between 0 - 1
training_data = np.divide(training_data, div_by)
validation_data = np.divide(validation_data, val_div_by)

#  generate random weights and bias for network
input_weights = np.random.uniform(low=weight_gen_min, high=weight_gen_max, size=(hidden_units, input_units))
output_weights = np.random.uniform(low=weight_gen_min, high=weight_gen_max, size=(output_units, hidden_units))
input_bias = np.random.uniform(low=weight_gen_min, high=weight_gen_max, size=(hidden_units, 1))
output_bias = np.random.uniform(low=weight_gen_min, high=weight_gen_max, size=(output_units, 1))

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
        #  list of input node activations to be sent to the hidden layer
        activation_list = []
        output_error = []
        hidden_error = []
        #  create target and output matrix to compare prediction to actual label
        target = int(training_labels[row])
        test_target_list.append(target)

        label_matrix = np.full(output_units, 0.1)
        label_matrix[target] = 0.9
        output_matrix = np.zeros(output_units)

        #  run through all training examples
        for unit in range(hidden_units):
            unit_output = np.dot(training_data[row, :], input_weights[unit, :]) + (1 * input_bias[unit])
            activation_list.append(float(expit(unit_output)))

        for percep in range(output_units):
            #  calculate prediction using perceptron algorithm
            percep_output = np.dot(activation_list, output_weights[percep, :]) + (1 * output_bias[percep])
            output_matrix[percep] = expit(percep_output)

            #  collect prediction values
            cur_predictions.append(output_matrix[percep])

            #  get error value for output layer
            output_error.append((label_matrix[percep] - output_matrix[percep]) * derivative(output_matrix[percep]))

        #  add the largest prediction value to an array of predictions
        prediction = np.argmax(np.array(cur_predictions))
        test_prediction_list.append(prediction)

        # update weights for output layer
        for o in range(output_units):
            output_bias[o, 0] += (learning_rate * output_error[o])
            output_weights[o, :] = output_weights[o, :] + (learning_rate * output_error[o] * activation_list[o])

        #  get error for hidden layer
        for h in range(hidden_units):
            error = np.dot(output_weights[:, h], output_error)
            hidden_error.append(error * derivative(activation_list[h]))

        #  update weights for hidden layer
        for h in range(hidden_units):
            input_bias[h, 0] += (learning_rate * hidden_error[h])
            input_weights[h, :] = input_weights[h, :] + (learning_rate * hidden_error[h] * training_data[row, :])

    #  check for correctness in training predictions
    for index in range(training_count):
        if test_prediction_list[index] == int(test_target_list[index]):
            test_correct += 1

    #  run through all validation examples
    for row in range(validation_count):
        #  array for storing prediction values
        cur_val_predictions = []
        #  list of input node activations to be sent to the hidden layer
        val_activation_list = []
        #  create target and output matrix to compare prediction to actual label
        val_target = int(validation_labels[row])
        val_target_list.append(val_target)

        for unit in range(hidden_units):
            unit_output = np.dot(validation_data[row, :], input_weights[unit, :]) + (1 * input_bias[unit])
            val_activation_list.append(float(expit(unit_output)))

        for percep in range(output_units):
            #  calculate prediction using perceptron algorithm
            percep_output = np.dot(val_activation_list, output_weights[percep, :]) + (1 * output_bias[percep])

            #  collect prediction values
            cur_val_predictions.append(expit(percep_output))

            #  add the largest prediction value to an array of predictions
        val_prediction_list.append(np.argmax(np.array(cur_val_predictions)))

    for index in range(validation_count):
        if val_prediction_list[index] == int(val_target_list[index]):
            val_correct += 1

    #  calculate and print accuracy
    prediction_accuracy[epoch] = (test_correct / training_count) * 100
    validation_accuracy[epoch] = (val_correct / validation_count) * 100
    print('Epoch {}: '.format(epoch), end='')
    print('training accuracy = {0:.2f}% '.format(prediction_accuracy[epoch]), end='')
    print('correct is {} / {}\n'.format(test_correct, training_count), end='')
    print('validation accuracy = {0:.2f}% '.format(validation_accuracy[epoch]), end='')
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
        plt.savefig('EX2.25_CM.png')

#  print run time
print('Program Runtime: {}'.format(datetime.now() - start))

#  plot and save accuracy graphs
test_accuracy = []
val_accuracy = []

for i in range(epochs):
    test_accuracy.append(prediction_accuracy[i])
    val_accuracy.append(validation_accuracy[i])

x = np.arange(epochs)
acc_fig = plt.figure()
plt.xlabel('Epochs', fontsize=12)
plt.ylabel('Accuracy', fontsize=12)
plt.plot(x, test_accuracy, linewidth=2, label='Test Accuracy')
plt.plot(x, val_accuracy, linewidth=2, label='Validation Accuracy')
plt.legend(loc='best')
acc_fig.savefig('EX2.25_ACC.png')
