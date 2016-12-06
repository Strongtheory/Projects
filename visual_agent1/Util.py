import math
import numpy

from PIL import Image
from PIL import ImageOps
from PIL import ImageChops as ic

# Image comparison taken from
# http://effbot.org/zone/pil-comparing-images.htm
def areImagesEqual(im1, im2):
    # print('Testing Unchanged')
    return ic.difference(Image.open(im1.visualFilename), 
        Image.open(im2.visualFilename)).getbbox() is None

# measure pixel difference
def getPixelRatio(im1, im2):
    exteriorPixelCount = (numpy.abs(numpy.subtract(
        numpy.matrix(im1).astype(float), numpy.matrix(im2).astype(float)
        )) > 50).sum()
    count = im1.size[0] * im1.size[1]
    return 1.0 - (exteriorPixelCount / \
        ((im1.size[0] * im1.size[1]) * 1.0))

# search for optimal solution
# @returns answer
def searchForSolution(problem, im, options):
    solution = -1
    optionCounter = 1
    highestRatio = 0.0
    correctChoice = True
    for choice in options:
        choiceInPlay = Image.open(problem.figures[choice].visualFilename).convert('L')
        #####################################################################
        # get ratio of pixels between the transferred im and option
        # im -> is aTob and aToc and img -> is the option
        # http://www.pyimagesearch.com/2014/09/15/python-compare-two-images/
        # http://stackoverflow.com/questions/37794520/how-do-i-implement-this-similarity-measure-in-python
        # get percentage of similarity based on ratio of pixels
        ratio = getPixelRatio(im, choiceInPlay)
        #####################################################################
        if correctChoice:
            correctChoice = False
            solution = optionCounter
            highestRatio = ratio
        else:
            # know compare from the first onwards
            # if false then set solution to the next option
            if ratio > highestRatio:
                solution = optionCounter
                highestRatio = ratio
        optionCounter += 1
    return highestRatio, solution

# find the optimal pixel difference
def compare(ab, ac, image_b, image_c, problem, options):
    if max(ac, ab) < .5:
        # no answer
        return max(ac, ab), (max(ac, ab), -1)
    if ab >= ac:
        return max(ac, ab), (searchForSolution(problem, ImageOps.mirror(image_c), options))
    return max(ac, ab), (searchForSolution(problem, ImageOps.mirror(image_b), options))

