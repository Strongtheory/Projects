from __future__ import division
from sklearn.metrics import mean_squared_error
from pybrain.tools.shortcuts import buildNetwork
from pybrain.supervised.trainers import BackpropTrainer

from datasets import format
import numpy as np
import matplotlib.pyplot as pylot


# retrieve dataset
cifar_dataset = format.cifar_setup()

training_size = len(cifar_dataset['train']['data'])
bias = range(int(0.1 * training_size), int(training_size), int(0.1 * training_size))

test_len = len(cifar_dataset['test']['data'][0])
network_shape = (test_len, test_len//2, test_len//4, 10)

training_error = [0] * len(bias)
testing_error = [0] * len(bias)

print('Neueral Network Run')
for i, j in enumerate(bias):
	cifar_dataset = format.cifar_neural_net(bias=j)
	net = buildNetwork(*network_shape)
	trainer = BackpropTrainer(net, cifar_dataset['train_neural_net'])
	trainer.trainOnDataset(cifar_dataset['train_neural_net'], 5)

	training_error[i] = mean_squared_error(cifar_dataset['train']['labels'],
								[net.activate(cifar_dataset['train']['data'][i])
								for k in xrange(len(cifar_dataset['train']['data']))])
	testing_error[i] = mean_squared_error(cifar_dataset['test']['labels'],
								[net.activate(cifar_dataset['test']['data'][i])
								for k in xrange(len(cifar_dataset['test']['data']))])

	print('Training Error: ' + str(training_error[i]))
	print('Testing Error: ' + str(testing_error[i]))
	print('===================================================')

# results
print('Plot')
pylot.figure()
pylot.title('Neural Net Results')
pylot.plot(bias, testing_error, '-', label='test error')
pylot.plot(bias, training_error, '-', label='train error')
pylot.legend()
pylot.xlabel('Training set error')
pylot.ylabel('Mean Squared Error')
pylot.show()
