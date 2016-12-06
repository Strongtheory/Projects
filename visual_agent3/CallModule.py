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
    if reflect[0] > remove[0]:
        if reflect[1][0] > remove[1][0]:
            solution = reflect[1][1]
    return solution


#####################################################################################


#####################################################################################
# final solve
def finalSolve3(count, xor, half):
    rc = 0
    ac = 0
    solution = -1
    if count[0] > rc:
        if count[1][0] >= ac:
            rc = count[0]
            ac = count[1][0]
            solution = count[1][1]
    if xor[0] > rc:
        if xor[1][0] >= ac:
            rc = xor[0]
            ac = xor[1][0]
            solution = xor[1][1]
    if half[0] > rc:
        if half[1][0] >= ac:
            solution = half[1][1]
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
# def unchange(problem, figures, options):
#     row = 0
#     column = 0
#     if um.unchangedCompare(um.totalCount(um.openAndInvert(figures[0])),
#                            um.totalCount(um.openAndInvert(figures[2]))):
#         if um.unchangedCompare(um.totalCount(um.openAndInvert(figures[3])),
#                                um.totalCount(um.openAndInvert(figures[5]))):
#             row = 1
#     if um.unchangedCompare(um.totalCount(um.openAndInvert(figures[0])),
#                            um.totalCount(um.openAndInvert(figures[6]))):
#         if um.unchangedCompare(um.totalCount(um.openAndInvert(figures[1])),
#                                um.totalCount(um.openAndInvert(figures[7]))):
#             column = 1
#     resultCount = um.unchangedMeasureAndCompare(row, column, um.totalCount(um.openAndInvert(figures[6])),
#                                                 um.totalCount(um.openAndInvert(figures[2])))
#     return max(row, column), um.searchForCount(problem, resultCount, options)


#####################################################################################


#####################################################################################
# XOR comparison
def xorCheck(problem, figures, options):
    axorb = um.xorCompare(um.openAndInvert(figures[0]), um.openAndInvert(figures[1]))
    dxore = um.xorCompare(um.openAndInvert(figures[3]), um.openAndInvert(figures[4]))
    btod = um.measurePixelDifference(axorb, um.openAndInvert(figures[2]))
    etoc = um.measurePixelDifference(dxore, um.openAndInvert(figures[5]))
    gxorh = um.xorCompare(um.openAndInvert(figures[6]), um.openAndInvert(figures[7]))
    return min(btod, etoc), um.searchForSolution(problem, gxorh, options)


#####################################################################################


#####################################################################################
# XOR comparison
# def xorCheckAlt(problem, figures, options):
#     bxord = um.xorCompare(um.openAndInvert(figures[1]), um.openAndInvert(figures[3]))
#     exorc = um.xorCompare(um.openAndInvert(figures[4]), um.openAndInvert(figures[2]))
#     exorg = um.xorCompare(um.openAndInvert(figures[4]), um.openAndInvert(figures[6]))
#     btod = um.measurePixelDifference(bxord, um.openAndInvert(figures[4]))
#     etoc = um.measurePixelDifference(exorc, um.openAndInvert(figures[5]))
#     etog = um.measurePixelDifference(exorg, um.openAndInvert(figures[7]))
#     gxorh = um.xorCompare(um.openAndInvert(figures[5]), um.openAndInvert(figures[7]))
#     return min(btod, etoc, etog), um.searchForSolution(problem, gxorh, options)


#####################################################################################


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
# def invertingAndCompare(problem, figures, options):
#     aTog = um.measurePixelDifference(ImageOps.flip(um.openAndInvert(figures[0])),
#                                      um.openAndInvert(figures[6]))
#     bToh = um.measurePixelDifference(ImageOps.flip(um.openAndInvert(figures[1])),
#                                      um.openAndInvert(figures[7]))
#     aToc = um.measurePixelDifference(ImageOps.mirror(um.openAndInvert(figures[0])),
#                                      um.openAndInvert(figures[2]))
#     dTof = um.measurePixelDifference(ImageOps.mirror(um.openAndInvert(figures[3])),
#                                      um.openAndInvert(figures[5]))
#     direction = um.determineDirection(aToc, dTof, aTog, bToh, figures)
#     return direction[1], um.searchForSolution(problem, direction[0], options)


#####################################################################################


#####################################################################################
# invert half
def invertHalf(problem, figures, options):
    aToc = um.measurePixelDifference(um.h_m(um.openAndInvert(figures[0])),
                                     um.openAndInvert(figures[2]))
    dTof = um.measurePixelDifference(um.h_m(um.openAndInvert(figures[3])),
                                     um.openAndInvert(figures[5]))
    return min(aToc, dTof), um.searchForSolution(problem,
                                                 um.h_m(um.openAndInvert(figures[6])), options)

#####################################################################################
