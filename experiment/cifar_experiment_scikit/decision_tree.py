from __future__ import division
from sklearn import tree
from sklearn.metrics import mean_squared_error

from datasets import format
import numpy as np
import matplotlib.pyplot as pylot


# retrieve dataset
cifar_dataset = format.cifar_setup()

# Train trees of different depths
max_depth = range(2, 20)
testing_error = [0] * len(max_depth)
training_error = [0] * len(max_depth)

print('Depth Size')
for i, j in enumerate(max_depth):
	t = tree.DecisionTreeClassifier(max_depth=j)
	clf = clf.fit(cifar_dataset['train']['data'], cifar_dataset['train']['labels'])

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
pylot.title('Decision Tree with variable depth')
pylot.plot(max_depth, testing_error, '-', label='test error')
pylot.plot(max_depth, training_error, '-', label='train error')
pylot.legend()
pylot.xlabel('max_depth')
pylot.ylabel('Mean Squared Error')
pylot.show()


# repeat but with variable training |set| and fixed depth + bias
training_set_size = len(cifar_dataset['train']['data'])
bias = range(int(0.1 * training_set_size), training_set_size, int(0.05 * training_set_size))
training_error = [0] * len(bias)
testing_error = [0] * len(bias)

print('Variable Set Size')
for i, j in enumerate(bias):
	t = tree.DecisionTreeClassifier(max_depth=7)
	clf = clf.fit(cifar_dataset['train']['data'][:j], cifar_dataset['train']['labels'][:j])

	training_error[i] = mean_squared_error(cifar_dataset['train']['labels'][:j],
												clf.predict(cifar_dataset['train']['data'][:j]))
	testing_error[i] = mean_squared_error(cifar_dataset['test']['labels'][:j],
												clf.predict(cifar_dataset['test']['data'][:j]))

	print('Training Error: ' + str(training_error[i]))
	print('Testing Error: ' + str(testing_error[i]))
	print('===================================================')

# results
print('Plot')
pylot.figure()
pylot.title('Boosted Decision Tree with variable set size')
pylot.plot(bias, testing_error, '-', label='test error')
pylot.plot(bias, training_error, '-', label='train error')
pylot.legend()
pylot.xlabel('Training set size')
pylot.ylabel('Mean Squared Error')
pylot.show()