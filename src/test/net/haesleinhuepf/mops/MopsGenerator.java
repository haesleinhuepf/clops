package net.haesleinhuepf.mops;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class MopsGenerator {
    static Mop[] mops = {
            new Mop("MaximumSphere", "filter().max", "Image imageOut, Image imageIn, SphereShape radius", "Maximum filter", "2D, 3D"),
            new Mop("MaximumRectangle", "filter().max", "Image imageOut, Image imageIn, RectangleShape radius", "Maximum filter", "2D"),
            new Mop("MaximumCube", "filter().max", "Image imageOut, Image imageIn, CubeShape radius", "Maximum filter", "3D"),

            new Mop("MinimumSphere", "filter().min", "Image imageOut, Image imageIn, SphereShape radius", "Maximum filter", "2D, 3D"),
            new Mop("MinimumRectangle", "filter().min", "Image imageOut, Image imageIn, RectangleShape radius", "Maximum filter", "2D"),
            new Mop("MinimumCube", "filter().min", "Image imageOut, Image imageIn, CubeShape radius", "Maximum filter", "3D"),

            new Mop("MeanSphere", "filter().mean", "Image imageOut, Image imageIn, SphereShape radius", "Maximum filter", "2D, 3D"),
            new Mop("MeanRectangle", "filter().mean", "Image imageOut, Image imageIn, RectangleShape radius", "Maximum filter", "2D"),
            new Mop("MeanCube", "filter().mean", "Image imageOut, Image imageIn, CubeShape radius", "Maximum filter", "3D"),

            new Mop("MedianSphere", "filter().median", "Image imageOut, Image imageIn, SphereShape radius", "Maximum filter", "2D, 3D"),
            new Mop("MedianRectangle", "filter().median", "Image imageOut, Image imageIn, RectangleShape radius", "Maximum filter", "2D"),
            new Mop("MedianCube", "filter().median", "Image imageOut, Image imageIn, CubeShape radius", "Maximum filter", "3D"),

            new Mop("OtsuThreshold", "threshold().otsu", "Image image", "Apply a threshold", "2D, 3D"),
            new Mop("PercentileThreshold", "threshold().percentile", "Image image", "Apply a threshold", "2D, 3D"),
            new Mop("MinimumThreshold", "threshold().minimum", "Image image", "Apply a threshold", "2D, 3D"),
            new Mop("MeanThreshold", "threshold().mean", "Image image", "Apply a threshold", "2D, 3D"),
            new Mop("MaxLikelihoodThreshold", "threshold().maxLikelihood", "Image image", "Apply a threshold", "2D, 3D"),
            new Mop("MaxEntropyThreshold", "threshold().maxEntropy", "Image image", "Apply a threshold", "2D, 3D"),
            new Mop("YenThreshold", "threshold().yen", "Image image", "Apply a threshold", "2D, 3D"),
            new Mop("TriangleThreshold", "threshold().triangle", "Image image", "Apply a threshold", "2D, 3D"),
            new Mop("MinErrorThreshold", "threshold().minError", "Image image", "Apply a threshold", "2D, 3D"),
            new Mop("MomentsThreshold", "threshold().moments", "Image image", "Apply a threshold", "2D, 3D"),
            new Mop("LiThreshold", "threshold().li", "Image image", "Apply a threshold", "2D, 3D"),
            new Mop("IJ1Threshold", "threshold().ij1", "Image image", "Apply a threshold", "2D, 3D"),
            new Mop("HuangThreshold", "threshold().huang", "Image image", "Apply a threshold", "2D, 3D"),
            new Mop("ShanbhagThreshold", "threshold().shanbhag", "Image image", "Apply a threshold", "2D, 3D"),
            new Mop("IntermodesThreshold", "threshold().intermodes", "Image image", "Apply a threshold", "2D, 3D"),
            new Mop("RenyiEntroopyThreshold", "threshold().renyiEntropy", "Image image", "Apply a threshold", "2D, 3D"),
            new Mop("IsoDataThreshold", "threshold().isoData", "Image image", "Apply a threshold", "2D, 3D"),

            new Mop("LocalSauvolaThresholdSphere", "threshold().localSauvolaThreshold", "BinaryImage imageOut, Image imageIn, SphereShape radius", "Generate a local threshold image", "2D, 3D"),
            new Mop("LocalSauvolaThresholdRectangle", "threshold().localSauvolaThreshold", "BinaryImage imageOut, Image imageIn, RectangleShape radius", "Generate a local threshold image", "2D, 3D"),
            new Mop("LocalSauvolaThresholdCube", "threshold().localSauvolaThreshold", "BinaryImage imageOut, Image imageIn, CubeShape radius", "Generate a local threshold image", "2D, 3D"),

            new Mop("Correlate", "filter().correlate", "Image input, Image psf", "Correlate", "2D, 3D"),
            new Mop("Convolve", "filter().convolve", "Image input, Image kernel", "Convolve an image with a kernel image", "2D, 3D"),

            new Mop("CreateGaussianKernel2D", "create().kernelGauss", "Number sigmaX,Number sigmaY", "Create a Gaussian kernel image", "2D"),
            new Mop("CreateGaussianKernel3D", "create().kernelGauss", "Number sigmaX,Number sigmaY, Number sigmaZ", "Create a Gaussian kernel image", "3D"),

            //new Mop("CreateImage2D", "create().img", "IntegerNumber width, IntegerNumber height", "", "2D"),
            //new Mop("CreateImage3D", "create().img", "IntegerNumber width, IntegerNumber height, IntegerNumber depth", "", "3D"),

            new Mop("CreateLogKernel2D", "create().kernelLog", "Number sigmaX, Number sigmaY", "", "2D"),
            new Mop("CreateLogKernel3D", "create().kernelLog", "Number sigmaX, Number sigmaY, Number sigmaZ", "", "3D"),

            new Mop("RichardsonLucyDeconvolution", "deconvolve().richardsonLucy", "Image input, Image kernel, IntegerNumber iterations", "", "2D, 3D"),

            new Mop("VarianceSphere", "filter().variance", "Image imageOut, Image imageIn, SphereShape radius", "", "2D, 3D"),
            new Mop("VarianceRectangle", "filter().variance", "Image imageOut, Image imageIn, RectangleShape radius", "", "2D"),
            new Mop("VarianceCube", "filter().variance", "Image imageOut, Image imageIn, CubeShape radius", "", "3D"),

            new Mop("Tubeness2D", "filter().tubeness", "Image image, Number sigma, Number calibrationX, Number calibrationY", "", "2D"),
            new Mop("Tubeness3D", "filter().tubeness", "Image image, Number sigma, Number calibrationX, Number calibrationY, Number calibrationZ", "", "3D"),

            new Mop("GaussianBlur2D", "filter().gauss", "Image image, Number sigmaX, Number sigmaY", "Gaussian blur of a 2D image", "2D"),
            new Mop("GaussianBlur3D", "filter().gauss", "Image image, Number sigmaX, Number sigmaY, Number sigmaZ", "Gaussian blur of a 3D image", "3D")

    };


    public static void main(String... args) throws IOException {

        StringBuilder menu = new StringBuilder();
        String menuTemplate = "Plugins>MOPS,              \"Mops example\",                   net.haesleinhuepf.mops.MopsExample\n";
        for (Mop mop : mops) {
            System.out.println(mop.name);

            // menu
            String opCategory = mop.op.replace('.', '#').split("#")[0].replace("()", "");
            menu.append(menuTemplate
                    .replace("MOPS", "MOPS>" + opCategory.substring(0, 1).toUpperCase() + opCategory.substring(1) + "(MOPS)")
                    .replace("Mops example", mop.name)
                    .replace("MopsExample", "implementations." + mop.name)
            );


            String templateFile = "src/main/java/net/haesleinhuepf/mops/MopsExample.java";
            String modFile = "src/main/java/net/haesleinhuepf/mops/implementations/" + mop.name + ".java";

            // read template
            StringBuilder template = new StringBuilder();
            for (String line : Files.readAllLines(Paths.get(templateFile), StandardCharsets.US_ASCII)) {
                template.append(line);
                template.append("\n");
            }
            String templateClass = template.toString();

            // deal with names
            templateClass = templateClass.replace("mopsExample", mop.name.substring(0, 1).toLowerCase() + mop.name.substring(1));
            templateClass = templateClass.replace("MopsExample", mop.name);
            templateClass = templateClass.replace("package net.haesleinhuepf.mops;", "package net.haesleinhuepf.mops.implementations;");

            templateClass = templateClass.replace("filter().gauss", mop.op);

            // deal with parameters
            String parameterJavaPlaceholder = "asImg(args[0]), asFloat(args[1]), asFloat(args[1])";
            String parameterDescriptionPlaceholder = "Image image, Number sigma";
            templateClass = templateClass.replace(parameterDescriptionPlaceholder, mop.parameters);

            StringBuilder javaParameters = new StringBuilder();
            int argCounter = 0;
            for (String parameter : mop.parameters.split(",")) {
                if (javaParameters.length() > 0) {
                    javaParameters.append(", ");
                }
                parameter = parameter.trim();
                //String variablename = parameter.split(" ")[1];
                if (parameter.startsWith("Image")) {
                    javaParameters.append("asImg(args[" + argCounter + "])");
                } else if (parameter.startsWith("BinaryImage")) {
                    javaParameters.append("asBinaryImg(args[" + argCounter + "])");
                } else if (parameter.startsWith("IntegerNumber")) {
                    javaParameters.append("asInteger(args[" + argCounter + "])");
                } else if (parameter.startsWith("Number")) {
                    javaParameters.append("asFloat(args[" + argCounter + "])");
                } else if (parameter.startsWith("Boolean")) {
                    javaParameters.append("asBoolean(args[" + argCounter + "])");
                } else if (parameter.startsWith("SphereShape")) {
                    javaParameters.append("asSphere(args[" + argCounter + "])");
                } else if (parameter.startsWith("RectangleShape")) {
                    javaParameters.append("asRectangle(args[" + argCounter + "])");
                } else if (parameter.startsWith("CubeShape")) {
                    javaParameters.append("asCube(args[" + argCounter + "])");
                } else {
                    javaParameters.append(parameter);
                }
                argCounter++;
            }
            templateClass = templateClass.replace(parameterJavaPlaceholder, javaParameters.toString());

            //System.out.println(templateClass);
            Files.write(Paths.get(modFile), templateClass.getBytes());
        }

        Files.write(Paths.get("src/main/resources/plugins.config"), menu.toString().getBytes());
    }
}
