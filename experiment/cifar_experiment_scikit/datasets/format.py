# Import and format the cifar datasets

from collection import Counter

import random
import cPickle
import numpy as np
from sklearn import preprocessing
from pybrain.datasets.classification import ClassificationDataSet


# primary cifar setup function
def cifar_setup(one_hot=False, ten_percent=False):
	data_ = training_batch() + [testing_batch()]
	joint_data = lambda r1, r2, cat: [x for sublist in [batch[cat] for batch in data_[r1:r2]] for x in sublist]
	# set the first 4 batches to training
	train_data, train_labels = shuffle_data(joint_data(0, 4, 'data'), joint_data(0, 4, 'labels'), ten_percent=ten_percent)
	# set the last 2 batches to testing
	test_data, test_labels = shuffle_data(joint_data(4, 6, 'data'), joint_data(4, 6, 'labels'), ten_percent=ten_percent)

	data = {
		'train': {
			'data': preprocessing.normalize(train_data),
			'labels': list(train_labels)
		},
		'test': {
			'data': preprocessing.normalize(test_data),
			'labels': list(test_labels)
		}
	}

	if one_hot:
		for tranche in ['train', 'test']:
			for i in xrange(len(data[tranche]['labels'])):
				klass = data[tranche]['labels'][i]
				one_hot_vector = [0] * 10
				one_hot_vector[klass] = 1
				data[tranche]['labels'][i] = one_hot_vector

	return data


# cifar neueral net operation
def cifar_neural_net(bias=None):
	data_ = cifar_setup(one_hot=True, ten_percent=False)
	x_Dimension = len(data_['train']['data'][0])
	data = ClassificationDataSet(x_Dimension, 10)
	if bias:
		max_sample = bias
	else:
		max_sample = len(data_['train']['data'])
	for i in xrange(max_sample):
		data.addSample(data_['train']['data'][i], data_['train']['labels'][i])
	data_['train_neural_net'] = data
	return data_


# load cifar testing batches
def testing_batch():
	with open('datasets/cifar-10-batches-py/test_batch', 'rb') as file:
		cifar_data = cPickle.load(file)
	return cifar_data


# load cifar training batches
def training_batch():
	cifar_data = []
	for batch in range(1, 6):
		with open('datasets/cifar-10-batches-py/data_batch_' + str(batch), 'rb') as file:
			cifar_data.append(cPickle.load(file))
	return cifar_data


# function to randomly split and shuffle data
def shuffle_data(data, labels, ten_percent=False):
	z = zip(data, labels)
	random.shuffle(z)
	if ten_percent:
		z = z[:int(len(z) * 0.1)]
	return zip(*z)

