from __future__ import division
from sklearn import svm
from sklearn.metrics import mean_squared_error

import datasets
import numpy as np
import matplotlib.pyplot as pylot


# retrieve dataset
cifar_dataset = format.cifar_setup()

# start
training_set_size = len(cifar_dataset['train']['data'])
bias = range(int(0.1 * training_set_size), training_set_size, int(0.05 * training_set_size))
training_error = [0] * len(bias)
testing_error = [0] * len(bias)

print('svm')
for i, j in enumerate(bias):
	clf = svm.SCV()
	clf = clf.fit(cifar_dataset['train']['data'][:j], cifar_dataset['train']['labels'][:j])

	training_error[i] = mean_squared_error(cifar_dataset['train']['labels'],
												clf.predict(cifar_dataset['train']['data']))
	testing_error[i] = mean_squared_error(cifar_dataset['test']['labels'],
												clf.predict(cifar_dataset['test']['data']))

	print('Training Error: ' + str(training_error[i]))
	print('Testing Error: ' + str(testing_error[i]))
	print('===================================================')


# results
print('Plot')
pylot.figure()
pylot.title('SVM with bias')
pylot.plot(bias, testing_error, '-', label='test error')
pylot.plot(bias, training_error, '-', label='train error')
pylot.legend()
pylot.xlabel('Training set size')
pylot.ylabel('Mean Squared Error')
pylot.show()

