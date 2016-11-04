import numpy

from PIL import Image
from PIL import ImageOps


# [A, B, C]
# [D, E, F]
# [G, H, ?]


# open and invert
def openAndInvert(im):
    return ImageOps.invert(Image.open(im.visualFilename).convert('L'))


# kullback-leibler
def kb_approach(im1, im2):
    first = openAndInvert(im1)
    second = openAndInvert(im2)
    # revise
    # http://stackoverflow.com/questions/18380037/computation-of-kullback-leibler-kl-distance-between-text-documents-using-numpy
    return numpy.sum(numpy.where(first != 0, first * numpy.log10(first / second)), 0)


# Measure Pixel Difference
# (Images are scanned!!!!)
def measurePixelDifference(im1, im2):
    epc = (numpy.abs(numpy.subtract(
        numpy.matrix(im1).astype(float),
        numpy.matrix(im2).astype(float))) > 50).sum()
    return 1 - (epc / (im1.size[0] * im1.size[1]))


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


def h_m(im):
    width, height = im.size
    result = Image.new('L', (width, height))
    result.paste(ImageOps.mirror(im.crop((0, 0, width >> 1, height))), (0, 0))
    result.paste(ImageOps.mirror(im.crop((width >> 1, 0, width, height))), (width >> 1, 0))
    return result
