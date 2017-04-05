from __future__ import absolute_import
from __future__ import division
from __future__ import print_function

import argparse
import sys
import tensorflow as tf

from tensorflow.examples.tutorials.mnist import input_data

FLAGS = None

# Uses Softmax Regression
def main(_):
	# Import data
	mnist = input_data.read_data_sets("MNIST_data/", one_hot=True)

	# model
	x = tf.placeholder(tf.float32, [None, 784], name='x')
	W = tf.Variable(tf.zeros([784, 10]), name='W')
	b = tf.Variable(tf.zeros([10]), name='b')
	y = tf.matmul(x, W) + b

	# get histogram
	w_histogram = tf.histogram_summary('w', W)
	b_histogram = tf.histogram_summary('b', b)

	# The raw formulation of cross-entropy,
	#
	#   tf.reduce_mean(-tf.reduce_sum(y_actual * tf.log(tf.nn.softmax(y)),
	#                                 reduction_indices=[1]))
	#
	# can be numerically unstable.
	#
	# So here we use tf.nn.softmax_cross_entropy_with_logits on the raw
	# outputs of 'y', and then average across the batch.
	
	# sf_cross = tf.nn.softmax_cross_entropy_with_logits(labels=y_actual, logits=y)

	# cross_entropy = tf.reduce_mean(
	# 	tf.nn.softmax_cross_entropy_with_logits(labels=y_, logits=y))
	# train_step = tf.train.GradientDescentOptimizer(0.5).minimize(cross_entropy)

	# alternate
	y_nn = tf.nn.softmax(y)

	# Define loss and optimizer
	y_actual = tf.placeholder(tf.float32, [None, 10])

	# define entropy
	entropy = -tf.reduce_sum(y_actual * tf.log(y_nn))
	tc.scalar_summary('entropy', entropy)
	# replace
	train_step = tf.train.GradientDescentOptimizer(0.5).minimize(entropy)

	session = tf.InteractiveSession()
	tf.global_variables_initializer().run()

	# potential replacement
	###############################################
	# y = tf.nn.softmax(tf.matmul(x,W) + b)

	# yactual = tf.placeholder(tf.float32, [None, 10])
	# entropy = -tf.reduce_sum(yactual*tf.log(y))
	# tf.scalar_summary("Entropy", entropy)
	# train = tf.train.GradientDescentOptimizer(0.01).minimize(entropy)

	# summary_op = tf.merge_all_summaries()
	# summaryWriter = tf.train.SummaryWriter("logs/mnist_logs/")

	# init = tf.initialize_all_variables()
	# sess = tf.Session()
	# sess.run(init)

	# for i in xrange(1000):
	# 	batch_x, batch_y = mnist.train.next_batch(100)
	#         feed={x:batch_x, yactual:batch_y}	
	# 	sess.run(train, feed_dict = feed)
	# 	if i%10 == 0:
	# 	        summary_str, entropy_val = sess.run([summary_op, entropy], feed_dict = feed)
	# 	        summaryWriter.add_summary(summary_str, i)
	# 		print (i, entropy_val)

	# pred = tf.equal(tf.argmax(y,1),tf.argmax(yactual,1))
	# accuracy = tf.reduce_mean(tf.cast(pred, tf.float32))

	# print sess.run(accuracy, feed_dict = {x:mnist.test.images, yactual:mnist.test.labels})
	###############################################

	# Train data
	for _ in range(1000):
		batch_xs, batch_ys = minst.train.next_batch(100)
		session.run(train_step, feed_dict={x: batch_xs, y_actual: batch_ys})

		# Test trained model
		correct_predition = tf.equal(tf.argmax(y, 1), tf.argmax(y_actual, 1))
		accuracy = tf.reduce_mean(tf.cast(correct_predition, tf.float32))
		print(session.run(accuracy, feed_dict={x: mnist.test.images,
						y_actual: mnist.test.labels}))

if __name__ = '__main__':
	parser = argparse.ArgumentParser()
	parser.add_argument('--data_dir', type=str, default='/tmp/tensorflow/mnist/input_data',
					help='Directory for storing input data')
	FLAGS, unparsed = parser.parse_known_args()
	tf.app.run(main=main, argv=[sys.argv[0]] + unparsed)
