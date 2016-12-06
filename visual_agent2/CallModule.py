import UtilityModule as um

import numpy
from PIL import Image
from PIL import ImageOps


# [A, B, C]
# [D, E, F]
# [G, H, ?]


#####################################################################################
def finalSolve2(remove, reflect):
    solution = remove[1][1]
    if reflect[0] >= remove[0]:
        if reflect[1][0] >= remove[1][0]:
            solution = reflect[1][1]
    return solution


#####################################################################################


#####################################################################################
# final solve
def finalSolve3(count, invert, unchanged, half, bit):
    solution = -1
    # if unchanged[0] > .5:
    #     if unchanged[1][0] >= 0:
    #         solution = unchanged[1][1]
    if half[0] > .9:
        if half[1][0] >= 0:
            solution = half[1][1]
    if bit[0] > half[0]:
        if bit[1][0] >= half[1][0]:
            solution = bit[1][1]
    if invert[0] > bit[0]:
        if invert[1][0] >= bit[1][0]:
            solution = invert[1][1]
    if count[0] > invert[0]:
        if count[1][0] >= invert[1][0]:
            solution = count[1][1]
    return solution


#####################################################################################


#####################################################################################
# check if a figure or shape is removed or created
# check based on pixel difference and ratio
def checkIfRemoved(problem, figure_a, figure_b, figure_c, options):
    image_a = numpy.matrix(um.openAndInvert(figure_a)).astype(float)
    image_b = numpy.matrix(um.openAndInvert(figure_b)).astype(float)
    image_c = numpy.matrix(um.openAndInvert(figure_c)).astype(float)

    aTransformb = Image.fromarray(image_c + (image_b - image_a))
    aTransformc = Image.fromarray(image_b + (image_c - image_a))

    solution_ab = um.searchForSolution(problem, aTransformb, options)
    solution_ac = um.searchForSolution(problem, aTransformc, options)

    # compare the solution ratio(s)
    return solution_ab[0], solution_ab if solution_ab[0] >= solution_ac[0] \
        else solution_ac[0], solution_ac


#####################################################################################


#####################################################################################
# check if the image was reoriented
# use ImageOps.mirror on image_a and then compare to image_b, image_c
# first get the pixel ratio and then determine the maximal percentage
# by comparing the mirrored image
# mirror flips image horizontally
def checkWhileMirrored(problem, figure_a, figure_b, figure_c, options):
    image_a = um.openAndInvert(figure_a)
    image_b = um.openAndInvert(figure_b)
    image_c = um.openAndInvert(figure_c)

    # check based on reflection using ImageOps
    # http://pillow.readthedocs.io/en/3.1.x/reference/ImageOps.html
    # http://stackoverflow.com/questions/14182642/fliping-image-mirror-image
    # http://effbot.org/imagingbook/imageops.htm#tag-ImageOps.mirror
    # first get the pixel difference then do a -> b and a -> comparison
    ab = um.measurePixelDifference(ImageOps.mirror(image_a), image_b)
    ac = um.measurePixelDifference(ImageOps.mirror(image_a), image_c)

    # get optimal max
    return um.compare(ab, ac, image_b, image_c, problem, options)


#####################################################################################


#####################################################################################
# pixel based
def unchanged(problem, figures, options):
    row = 0
    column = 0
    if um.unchangedCompare(um.totalCount(um.openAndInvert(figures[0])),
                           um.totalCount(um.openAndInvert(figures[2]))):
        if um.unchangedCompare(um.totalCount(um.openAndInvert(figures[3])),
                               um.totalCount(um.openAndInvert(figures[5]))):
            row = 1
    if um.unchangedCompare(um.totalCount(um.openAndInvert(figures[0])),
                           um.totalCount(um.openAndInvert(figures[6]))):
        if um.unchangedCompare(um.totalCount(um.openAndInvert(figures[1])),
                               um.totalCount(um.openAndInvert(figures[7]))):
            column = 1
    resultCount = um.unchangedMeasureAndCompare(row, column, um.totalCount(um.openAndInvert(figures[6])),
                                                um.totalCount(um.openAndInvert(figures[2])))
    return max(row, column), um.searchForCount(problem, resultCount, options)


#####################################################################################


# bitwise
def bitwise(problem, figures, options):
    aTob = um.bitwiseCheck(um.openAndInvert(figures[0]),
                           um.openAndInvert(figures[1]))
    dToe = um.bitwiseCheck(um.openAndInvert(figures[3]),
                           um.openAndInvert(figures[4]))
    bTod = um.measurePixelDifference(aTob, um.openAndInvert(figures[2]))
    eToc = um.measurePixelDifference(dToe, um.openAndInvert(figures[5]))
    return min(bTod, eToc), um.searchForSolution(problem, um.bitwiseCheck(um.openAndInvert(figures[6]),
                                                                          um.openAndInvert(figures[7])),
                                                 options)


#####################################################################################
# count and compare
def pixelCount(problem, figures, options):
    row = 0
    column = 0
    if um.transformCompare(um.totalCount(um.openAndInvert(figures[0])),
                           um.totalCount(um.openAndInvert(figures[1])),
                           um.totalCount(um.openAndInvert(figures[2]))):
        if um.transformCompare(um.totalCount(um.openAndInvert(figures[3])),
                               um.totalCount(um.openAndInvert(figures[4])),
                               um.totalCount(um.openAndInvert(figures[5]))):
            row = 1
    if um.transformCompare(um.totalCount(um.openAndInvert(figures[0])),
                           um.totalCount(um.openAndInvert(figures[3])),
                           um.totalCount(um.openAndInvert(figures[6]))):
        if um.transformCompare(um.totalCount(um.openAndInvert(figures[1])),
                               um.totalCount(um.openAndInvert(figures[4])),
                               um.totalCount(um.openAndInvert(figures[7]))):
            column = 1
    resultCount = um.measureAndCompare(row, column, um.totalCount(um.openAndInvert(figures[2])),
                                       um.totalCount(um.openAndInvert(figures[5])),
                                       um.totalCount(um.openAndInvert(figures[6])),
                                       um.totalCount(um.openAndInvert(figures[7])))
    return max(row, column), um.searchForCount(problem, resultCount, options)


#####################################################################################


#####################################################################################
# try inverting both horizontally and vertically
def invertingAndCompare(problem, figures, options):
    av_invert = ImageOps.flip(um.openAndInvert(figures[0]))
    bv_invert = ImageOps.flip(um.openAndInvert(figures[1]))
    ah_invert = ImageOps.mirror(um.openAndInvert(figures[0]))
    dh_invert = ImageOps.mirror(um.openAndInvert(figures[3]))
    aTog = um.measurePixelDifference(av_invert, um.openAndInvert(figures[6]))
    bToh = um.measurePixelDifference(bv_invert, um.openAndInvert(figures[7]))
    aToc = um.measurePixelDifference(ah_invert, um.openAndInvert(figures[2]))
    dTof = um.measurePixelDifference(dh_invert, um.openAndInvert(figures[5]))
    direction = um.determineDirection(aToc, dTof, aTog, bToh, figures)
    return direction[1], um.searchForSolution(problem, direction[0], options)


#####################################################################################


#####################################################################################
# invert half
def invertHalf(problem, figures, options):
    invertA = um.halfModify(um.openAndInvert(figures[0]))
    invertD = um.halfModify(um.openAndInvert(figures[3]))
    aToc = um.measurePixelDifference(invertA, um.openAndInvert(figures[2]))
    dTof = um.measurePixelDifference(invertD, um.openAndInvert(figures[5]))
    return min(aToc, dTof), um.searchForSolution(problem, um.halfModify(um.openAndInvert(figures[6])), options)

#####################################################################################
