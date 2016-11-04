# Your Agent for solving Raven's Progressive Matrices. You MUST modify this file.
#
# You may also create and submit new files in addition to modifying this file.
#
# Make sure your file retains methods with the signatures:
# def __init__(self)
# def Solve(self,problem)
#
# These methods will be necessary for the project's main method to run.

# Install Pillow and uncomment this line to access image processing.
# from PIL import Image
# import numpy


class Agent:
    # The default constructor for your Agent. Make sure to execute any
    # processing necessary before your Agent starts solving problems here.
    #
    # Do not add any variables to this signature; they will not be used by
    # main().
    def __init__(self):
        pass

    # The primary method for solving incoming Raven's Progressive Matrices.
    # For each problem, your Agent's Solve() method will be called. At the
    # conclusion of Solve(), your Agent should return an int representing its
    # answer to the question: 1, 2, 3, 4, 5, or 6. Strings of these ints 
    # are also the Names of the individual RavensFigures, obtained through
    # RavensFigure.getName(). Return a negative number to skip a problem.
    #
    # Make sure to return your answer *as an integer* at the end of Solve().
    # Returning your answer as a string may cause your program to crash.
    def Solve(self, problem):
        solution = -1
        options_two = ['1', '2', '3', '4', '5', '6']
        options_three = ['1', '2', '3', '4', '5', '6', '7', '8']
        vertical_direction = {}
        horizontal_direction = {}
        if problem.problemType == '2x2':
            # In 2x2, the figures are named A, B, and C. A is to B as C is to one of
            # the answer choices. Similarly, A is to C as B is to one of the answer
            # choices. In 2x2 problems, relationships are present both across the rows
            # and down the columns. The answer choices are named 1 through 6.
            # [A, B]
            # [C, ?]
            ##########################################################################################
            print('2x2: ' + problem.name)
            ##########################################################################################
            a = problem.figures['A']
            b = problem.figures['B']
            c = problem.figures['C']
            ##########################################################################################
        else:
            # In 3x3, the figures in the first row are named from left to right A, B,
            # and C. The figures in the second row are named from left to right D, E,
            # and F. The figures in the third row are named from left to right G and H.
            # Relationships are present across rows and down columns: A is to B is to
            # C as D is to E is to F as G is to H is to one of the answer choices. A is
            # to D is G as B is to E is to H as C is to F is to one of the answer
            # choices. The answer choices are named 1 through 8.
            # [A, B, C]
            # [D, E, F]
            # [G, H, ?]
            print('3x3: ' + problem.name)
        return solution
