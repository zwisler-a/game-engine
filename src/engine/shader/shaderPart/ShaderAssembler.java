package engine.shader.shaderPart;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ShaderAssembler {


    public static void assamble(ShaderPart[] partials) {
        ArrayList<Uniform> uniforms = new ArrayList<>();
        ArrayList<ProcessedShaderPart> vertexParts = new ArrayList<>();
        ArrayList<ProcessedShaderPart> fragmentParts = new ArrayList<>();
        ArrayList<ShaderAttribute> attribs = new ArrayList<>();
        for (ShaderPart part : partials) {
            for (Uniform uni : part.getUniforms()) {
                uniforms.add(uni);
            }
            for (ShaderAttribute attrib : part.getAttributes()) {
                attribs.add(attrib);
            }
            vertexParts.add(processShaderFile(part.getVertexShaderPart(), part.getClass().getName()));
            fragmentParts.add(processShaderFile(part.getFragmentShaderPart(), part.getClass().getName()));
        }

        checkAttributeMismatch(attribs);
    }


    public static String generateVariables(ArrayList<ShaderAttribute> azztibs, ArrayList<Uniform> uniforms, ArrayList<Constants> constants) {
        StringBuilder vars = new StringBuilder();
        for (ShaderAttribute attrib : azztibs) {
            vars.append("in ")
                    .append(attrib.getType())
                    .append(attrib.getName())
                    .append(";\n");
        }
        for (Uniform uniform : uniforms) {
            vars.append("in ")
                    .append(uniform.getType())
                    .append(uniform.getName())
                    .append(";\n");
        }
        for (Constants constant : constants) {
            vars.append(constant.getConstant());
        }
        return vars.toString();
    }


    public static String generateMainMethod(ArrayList<ProcessedShaderPart> methods) {
        StringBuilder main = new StringBuilder().append("void main(void){\n");
        StringBuilder methodsString = new StringBuilder();
        for (ProcessedShaderPart method : methods) {
            main.append(method.name);
            main.append("();\n");

            methodsString.append(method.method);
            methodsString.append("\n");
        }
        main.append("\n}\n");
        main.append(methodsString);
        return main.toString();
    }


    public static void checkAttributeMismatch(ArrayList<ShaderAttribute> attribs) {
        for (ShaderAttribute attribute : attribs) {
            for (ShaderAttribute control : attribs) {
                if (!control.equals(attribute)) {
                    if (attribute.getPosition() == control.getPosition() || attribute.getName().equals(control.getName())) {
                        throw new Error("Shader Attribute missmatch " + attribute.getName() + " " + attribute.getPosition());
                    }
                }
            }
        }
    }

    private static ProcessedShaderPart processShaderFile(String path, String name) {
        String source = readShaderCodeFile(path);
        return new ProcessedShaderPart(convertMainMethod(source, name), name);
    }

    private static String convertMainMethod(String source, String name) {
        source = source.substring(source.indexOf("void main(void) {"));
        source.replace("void main(void) {", "void " + name + "(void) {");
        return source;
    }


    private static String readShaderCodeFile(String path) {
        try {
            StringBuilder shaderSource = new StringBuilder();
            BufferedReader reader = new BufferedReader(new FileReader(path));
            String line;
            while ((line = reader.readLine()) != null) {
                shaderSource.append(line).append("\n");
            }
            reader.close();
            return shaderSource.toString();
        } catch (IOException e) {
            System.err.println("Could not read file.");
            e.printStackTrace();
            System.exit(-1);
        }
        return "Error";
    }


    private static class ProcessedShaderPart {
        public String name;
        public String method;

        public ProcessedShaderPart(String name, String method) {

        }
    }

}
