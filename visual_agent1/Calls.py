import Util
import numpy

from PIL import Image
from PIL import ImageOps
from PIL import ImageFilter

# final solve
def solve2x2(remove, reflect):
    solution = remove[1][1]
    if reflect[0] >= remove[0]:
        if reflect[1][0] >= remove[1][0]:
            solution = reflect[1][1]
    return solution

# check if a figure or shape is removed or created
# check based on pixel difference and ratio
def checkIfRemoved(problem, figure_a, figure_b, figure_c, options):
    image_a = numpy.matrix(Image.open(figure_a.visualFilename).convert('L')).astype(float)
    image_b = numpy.matrix(Image.open(figure_b.visualFilename).convert('L')).astype(float)
    image_c = numpy.matrix(Image.open(figure_c.visualFilename).convert('L')).astype(float)

    aTransformb = Image.fromarray(image_c + (image_b - image_a))
    aTransformc = Image.fromarray(image_b + (image_c - image_a))

    solution_ab = Util.searchForSolution(problem, aTransformb, options)
    solution_ac = Util.searchForSolution(problem, aTransformc, options)

    # compare the solution ratio(s)
    return solution_ab[0], solution_ab if solution_ab[0] >= solution_ac[0] \
                else solution_ac[0], solution_ac

# check if the image was reoriented
# use ImageOps.mirror on image_a and then compare to image_b, image_c
# first get the pixel ratio and then determine the maximal percentage
# by comparing the mirrored image
# mirror flips image horizontally
def checkWhileMirrored(problem, figure_a, figure_b, figure_c, options):
    image_a = Image.open(figure_a.visualFilename).convert('L')
    image_b = Image.open(figure_b.visualFilename).convert('L')
    image_c = Image.open(figure_c.visualFilename).convert('L')

    # check based on reflection using ImageOps
    # http://pillow.readthedocs.io/en/3.1.x/reference/ImageOps.html
    # http://stackoverflow.com/questions/14182642/fliping-image-mirror-image
    # http://effbot.org/imagingbook/imageops.htm#tag-ImageOps.mirror
    # first get the pixel difference then do a -> b and a -> comparison
    ab = Util.getPixelRatio(ImageOps.mirror(image_a), image_b)
    ac = Util.getPixelRatio(ImageOps.mirror(image_a), image_c)

    # get optimal max
    return Util.compare(ab, ac, image_b, image_c, problem, options)
