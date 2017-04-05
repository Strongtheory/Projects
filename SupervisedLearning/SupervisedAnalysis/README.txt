Abhishek Deo
GITD: 902866240
Homework 1 Readme

* All the algorithms were run within weka using the GUI and used no external resources with the exception of the SVM algorithm
  which required a libsvm dependency which is provided in the submission.

* Provided in the submission are the full .arff files for each dataset, the training portion, and the testing portion that I used.
  The files are separated based on their names are properly labeled. I ran the algorithms on both the testing and training data portions
  with variations which I will explain below for each algorithm.

1.) Decision Trees (J48 Algorithm): The variations are based on the confidenceFactor value in the settings. I changed the values from 0.1 up till 0.5
    with increments of 0.1 resulting in 5 runs for both training and testing.
2.) Boosting (Adaboost with J48): This is exactly similar to the decision tree variations where I changed the confidenceFactor value in the settings.
    I ran adaboost layered with the J48 decision tree classifier.
3.) Neural Networks (Multilayer-Perception): I ran the neural network algorithms with three major variations. Both the training and testing data portions
    for both datasets were run with changes on the learning rate, momentum, and time (epoch). The combination of learning rate and momentum are (learningRate,
    momentum): (0.3, 0.2), (0.4, 0.1), and (0.2, 0.3). Within each combination I ran both portions 5 times at different epoch values starting from 500 till 4500
    at increments of 1000. This results in a total of 120 runs for all of neural networks and 6 lines with 5 points each. (3 lines for training and three lines for testing)
    I say 120 because this was repeated twice, one time for each dataset.
4.) kNN (Ibk): I ran this classifier in two cases, one for weighted and one for unweighted. The only changing factor was the value of K which was at increments of 10
    starting from 10 and ending at 50. The weighted case was the second option where it says "weight by 1/distance" and the unweighted case was when the option states
    "No distance weighting".
5.) SVM (libsvm): The SVM algorithm was run twice for both the testing and training portions of both datasets. I ran the polykernel function with change in the degree
    value ranging from 1 to 5 with increments of 1. I ran the RBF function with changes in the gamma value in incrememts of 0.25. [0.01, 0.25, 0.5, 0.75, 1.0] This function
    required me to have the libsvm dependency in my class path (libsvm jar file) to be able to run the classifier which is included in the submission.

** In addition I added the results I recorded in a seperate folder for reference of what values I received when running these algorithms on the datasets.