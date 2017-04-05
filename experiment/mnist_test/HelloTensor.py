import tensorflow as tf
import numpy as np

x = np.random.rand(100).astype(np.float32)
y = x * 0.1 + 0.3

w = tf.Variable(tf.random_uniform([1], -1.0, 1.0))
b = tf.Variable(tf.zeros([1]))

model = w * x + b
yield_loss = tf.reduce_mean(tf.square(model - y))
optimizer = tf.train.GradientDescentOptimizer(0.5)
train = optimizer.minimize(yield_loss)

start = tf.initialize_all_variables()

# start and run session
session = tf.Session()
session.run(start)

for step in xrange(400):
	session.run(train)
	if step % 20 == 0:
		print(step, session.run(w), session.run(b))
