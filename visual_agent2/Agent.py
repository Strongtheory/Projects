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

import CallModule as cm
import UtilityModule as um


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
        options_two = ['1', '2', '3', '4', '5', '6']
        options_three = ['1', '2', '3', '4', '5', '6', '7', '8']
        if problem.problemType == '2x2':
            # In 2x2, the figures are named A, B, and C. A is to B as C is to one of
            # the answer choices. Similarly, A is to C as B is to one of the answer
            # choices. In 2x2 problems, relationships are present both across the rows
            # and down the columns. The answer choices are named 1 through 6.
            # [A, B]
            # [C, ?]
            # print('2x2: ' + problem.name)
            #####################################################################################
            # first check if unchanged
            # if um.areEqual(figure_a, figure_b):
            #     for i in range(0, 6):
            #         choice = problem.figures[str(i + 1)]
            #         if um.areEqual(figure_c, choice):
            #             solution = i + 1
            #             return solution
            # elif um.areEqual(figure_a, figure_c):
            #     for i in range(0, 6):
            #         choice = problem.figures[str(i + 1)]
            #         if um.areEqual(figure_b, choice):
            #             solution = i + 1
            #             return solution
            #####################################################################################
            a = problem.figures['A']
            b = problem.figures['B']
            c = problem.figures['C']
            #####################################################################################
            # first check if unchanged
            if um.areEqual(a, b):
                for i in range(0, 6):
                    choice = problem.figures[str(i + 1)]
                    if um.areEqual(c, choice):
                        solution = i + 1
                        return solution
            elif um.areEqual(a, c):
                for i in range(0, 6):
                    choice = problem.figures[str(i + 1)]
                    if um.areEqual(b, choice):
                        solution = i + 1
                        return solution
            #####################################################################################
            removed = cm.checkIfRemoved(problem, a, b, c, options_two)
            reflected = cm.checkWhileMirrored(problem, a, b, c, options_two)
            solution = cm.finalSolve2(removed, reflected)
            #####################################################################################
        else:
            # In 3x3, the figures in the first row are named from left to right A, B,
            # and C. The figures in the second row are named from left to right D, E,
            # and F. The figures in the third row are named from left to right G and H.
            # Relationships are present across rows and down columns: A is to B is to
            # C as D is to E is to F as G is to H is to one of the answer choices. A is
            # to D is G as B is to E is to H as C is to F is to one of the answer
            # choices. The answer choices are named 1 through 6.
            # [A, B, C]
            # [D, E, F]
            # [G, H, ?]
            # print('3x3: ' + problem.name)
            #####################################################################################
            a = problem.figures['A']
            b = problem.figures['B']
            c = problem.figures['C']
            d = problem.figures['D']
            e = problem.figures['E']
            f = problem.figures['F']
            g = problem.figures['G']
            h = problem.figures['H']
            # because too many params to pass in
            figures_three = [a, b, c, d, e, f, g, h]
            #####################################################################################
            # vertical = {}
            # horizontal = {}
            # if um.rmsDiff(a, c) < um.rmsDiff(a, g):
            #     if um.rmsDiff(e, f) < um.rmsDiff(e, h):
            #         best = max(um.rmsDiff(a, c), um.rmsDiff(e, f))
            #         for i in range(0, 8):
            #             choice = problem.figures[str(i + 1)]
            #             check = um.rmsDiff(h, choice)
            #             diff = best - check
            #             horizontal[i + 1] = diff
            #         return min(horizontal.items(), key=lambda x: x[1])[0]
            # elif um.rmsDiff(a, c) > um.rmsDiff(a, g):
            #     if um.rmsDiff(e, f) > um.rmsDiff(e, h):
            #         best = max(um.rmsDiff(a, g), um.rmsDiff(e, h))
            #         for i in range(0, 8):
            #             choice = problem.figures[str(i + 1)]
            #             check = um.rmsDiff(h, choice)
            #             diff = best - check
            #             vertical[i + 1] = diff
            #         return max(vertical.items(), key=lambda x: x[1])[0]
            #####################################################################################
            count = cm.pixelCount(problem, figures_three, options_three)
            invert = cm.invertingAndCompare(problem, figures_three, options_three)
            unchanged = cm.unchanged(problem, figures_three, options_three)
            half = cm.invertHalf(problem, figures_three, options_three)
            bit = cm.bitwise(problem, figures_three, options_three)
            solution = cm.finalSolve3(count, invert, unchanged, half, bit)
            #####################################################################################
        return solution
