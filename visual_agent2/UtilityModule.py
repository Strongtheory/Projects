import numpy
import math
import operator
from functools import reduce

from PIL import Image
from PIL import ImageOps
from PIL import ImageChops
from PIL import ImageMath


# [A, B, C]
# [D, E, F]
# [G, H, ?]


# open and invert
def openAndInvert(im):
    return ImageOps.invert(Image.open(im.visualFilename).convert('L'))


# Measure Pixel Difference
# (Images are scanned!!!!)
def measurePixelDifference(im1, im2):
    exteriorPixelCount = (numpy.abs(numpy.subtract(
        numpy.matrix(im1).astype(float),
        numpy.matrix(im2).astype(float))) > 50).sum()
    return 1.0 - (exteriorPixelCount / (im1.size[0] * im1.size[1]))


# find solution
def searchForSolution(problem, result, options):
    answer = -1
    lowest = 0.0
    optionCounter = 1
    firstOption = True
    for choice in options:
        ratio = measurePixelDifference(result, openAndInvert(problem.figures[choice]))
        if firstOption:
            firstOption = False
            answer = optionCounter
            lowest = ratio
        else:
            if ratio > lowest:
                answer = optionCounter
                lowest = ratio
        optionCounter += 1
    return lowest, answer


# same as search for solution but instead inputted number
def searchForCount(problem, total, options):
    answer = -1
    lowest = 0.0
    optionCounter = 1
    firstOption = True
    for choice in options:
        diff = abs(total - ((numpy.matrix(openAndInvert(problem.figures[choice])).astype(float) > 200).sum()))
        if diff < 300:
            if firstOption:
                firstOption = False
                answer = optionCounter
                lowest = diff
            else:
                if diff < lowest:
                    answer = optionCounter
                    lowest = diff
        optionCounter += 1
    return lowest, answer


# rms difference
# http://effbot.org/zone/pil-comparing-images.htm
def rmsDiff(im1, im2):
    """Calculate the root-mean-square difference between two images"""
    first = openAndInvert(im1)
    second = openAndInvert(im2)
    h = ImageChops.difference(first, second).histogram()

    # calculate rms
    return math.sqrt(reduce(operator.add,
                            map(lambda h, i: h * (i ** 2), h, range(256))
                            ) / (float(first.size[0]) * first.size[1]))


# bitwise
def bitwiseCheck(a, b):
    return ImageMath.eval("(a | b) & b", a=a, b=b)


# 2x2
def areEqual(im1, im2):
    first = Image.open(im1.visualFilename)
    second = Image.open(im2.visualFilename)
    return ImageChops.difference(first, second).getbbox() is None


# find the optimal pixel difference
def compare(ab, ac, image_b, image_c, problem, options):
    if max(ac, ab) < .5:
        # no answer
        return max(ac, ab), (max(ac, ab), -1)
    if ab >= ac:
        return max(ac, ab), (searchForSolution(problem, ImageOps.mirror(image_c), options))
    return max(ac, ab), (searchForSolution(problem, ImageOps.mirror(image_b), options))


# half and modify
def halfModify(im):
    width, height = im.size
    result = Image.new('L', (width, height))
    result.paste(ImageOps.mirror(im.crop((0, 0, width >> 1, height))), (0, 0))
    result.paste(ImageOps.mirror(im.crop((width >> 1, 0, width, height))), (width >> 1, 0))
    return result


# simple transform and compare formula
def transformCompare(a, b, c):
    return c - 300 < (b - a) + b < c + 300


# unchanged
def unchangedCompare(a, b):
    return b - 200 < a * 2 < b + 200


# unchanged measureAndCompare
def unchangedMeasureAndCompare(row, column, g, c):
    if row >= column:
        return g << 1
    else:
        return c << 1


# measure the transform(s)
def measureAndCompare(row, column, c, f, g, h):
    if row >= column:
        return (h - g) + h
    else:
        return (f - c) + f


# get total amount of pixels
def totalCount(im):
    return (numpy.matrix(im).astype(float) > 200).sum()


# determine better direction of transformations
def determineDirection(a, b, c, d, figures):
    if min(a, b) > min(c, d):
        result = ImageOps.mirror(openAndInvert(figures[6]))
        optimal = min(a, b)
    else:
        result = ImageOps.flip(openAndInvert(figures[2]))
        optimal = min(c, d)
    return result, optimal
