package generator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

/**
 * Created by jinesh francis on 21/7/17.
 */

public class PojoGenerator {
    public static String PATH;
    public static void generate(String canonicalPath, String jsonString, String className, boolean isAnnotationRequired, boolean isNeedConstructor, boolean isGetterSetterRequired){
        PojoGenerator.PATH=canonicalPath;
        try {
            JSONObject jsonObject=new JSONObject(jsonString);
            StringBuilder variableBuilder=new StringBuilder("");
            StringBuilder importsBuilder=new StringBuilder("");
            StringBuilder getterSetterBuilder=new StringBuilder("");
            StringBuilder constructorBuilder=new StringBuilder("");
            StringBuilder constructorAssignmentBuilder=new StringBuilder("");
            extractVariablesFromJsonObject(jsonObject,className,variableBuilder,importsBuilder,getterSetterBuilder,constructorBuilder,constructorAssignmentBuilder,isAnnotationRequired,isNeedConstructor,isGetterSetterRequired);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            try {
                JSONArray jsonArray=new JSONArray(jsonString);
                if(jsonArray.length()>0){
                    JSONObject jsonObject=jsonArray.getJSONObject(0);
                    StringBuilder variableBuilder=new StringBuilder("");
                    StringBuilder importsBuilder=new StringBuilder("");
                    StringBuilder getterSetterBuilder=new StringBuilder("");
                    StringBuilder constructorBuilder=new StringBuilder("");
                    StringBuilder constructorAssignmentBuilder=new StringBuilder("");
                    extractVariablesFromJsonObject(jsonObject,className,variableBuilder,importsBuilder,getterSetterBuilder,constructorBuilder,constructorAssignmentBuilder,isAnnotationRequired,isNeedConstructor,isGetterSetterRequired);
                }
            } catch (Exception e1) {
                e1.printStackTrace();
                System.out.println(e1.getMessage());
            }
        }
    }

    private static File createFile(String name, String path) {
        File file=new File(path+"/"+name+".java");
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    private static void extractVariablesFromJsonObject(JSONObject jsonObject, String name, StringBuilder variableBuilder, StringBuilder importsBuilder, StringBuilder getterSetterBuilder, StringBuilder constructorBuilder, StringBuilder constructorAssignmentBuilder, boolean isAnnotationRequired, boolean isNeedConstructor, boolean isGetterSetterRequired) {
        if(jsonObject==null){
            return;
        }
        File file=createFile(name,PATH);
        BufferedWriter bufferedWriter=null;
        try {
            bufferedWriter=new BufferedWriter(new FileWriter(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(isAnnotationRequired)
        importsBuilder.append("import com.google.gson.annotations.Expose;\n" + "import com.google.gson.annotations.SerializedName;").append("\n");
        Iterator<String> keyIterator=jsonObject.keys();
        if(isNeedConstructor) {
            constructorBuilder.append("  public ").append(name).append("(){").append("\n").append("  }\n");
            constructorBuilder.append("  public ").append(name).append("(");
        }
        while (keyIterator.hasNext()){
            String field=keyIterator.next();
            if(isAnnotationRequired) {
                variableBuilder.append("  @SerializedName(").append("\"").append(field).append("\"").append(")").append("\n");
                variableBuilder.append("  @Expose").append("\n");
            }
            try {
                StringBuilder fieldBuilder=new StringBuilder(field);
                Integer intValue=jsonObject.getInt(field);
                variableBuilder.append("  private Integer ").append(field).append(";");
                variableBuilder.append("\n");
                if(isNeedConstructor) {
                    constructorBuilder.append("Integer ").append(field).append(",");
                    constructorAssignmentBuilder.append("   this.").append(field).append("=").append(field).append(";").append("\n");
                }
                String c=fieldBuilder.substring(0,1);
                fieldBuilder.deleteCharAt(0);
                fieldBuilder.insert(0,c.toUpperCase());
                createGetterSetterMathods(getterSetterBuilder,fieldBuilder,field,"Integer");
            } catch (JSONException e) {
                try {
                    jsonObject.getLong(field);
                    variableBuilder.append("  private Long ").append(field).append(";");
                    variableBuilder.append("\n");
                    if(isNeedConstructor) {
                        constructorBuilder.append("Long ").append(field).append(",");
                        constructorAssignmentBuilder.append("   this.").append(field).append("=").append(field).append(";").append("\n");
                    }
                    StringBuilder fieldBuilder=new StringBuilder(field);
                    String c=fieldBuilder.substring(0,1);
                    fieldBuilder.deleteCharAt(0);
                    fieldBuilder.insert(0,c.toUpperCase());
                    createGetterSetterMathods(getterSetterBuilder,fieldBuilder,field,"Long");
                } catch (JSONException e1) {
                    try {
                        jsonObject.getDouble(field);
                        variableBuilder.append("  private Double ").append(field).append(";");
                        variableBuilder.append("\n");
                        if(isNeedConstructor) {
                            constructorBuilder.append("Double ").append(field).append(",");
                            constructorAssignmentBuilder.append("   this.").append(field).append("=").append(field).append(";").append("\n");
                        }
                        StringBuilder fieldBuilder=new StringBuilder(field);
                        String c=fieldBuilder.substring(0,1);
                        fieldBuilder.deleteCharAt(0);
                        fieldBuilder.insert(0,c.toUpperCase());
                        createGetterSetterMathods(getterSetterBuilder,fieldBuilder,field,"Double");
                    } catch (JSONException e2) {
                        try {
                            jsonObject.getBoolean(field);
                            variableBuilder.append("  private Boolean ").append(field).append(";");
                            variableBuilder.append("\n");
                            if(isNeedConstructor) {
                                constructorBuilder.append("Boolean ").append(field).append(",");
                                constructorAssignmentBuilder.append("   this.").append(field).append("=").append(field).append(";").append("\n");
                            }
                            StringBuilder fieldBuilder=new StringBuilder(field);
                            String c=fieldBuilder.substring(0,1);
                            fieldBuilder.deleteCharAt(0);
                            fieldBuilder.insert(0,c.toUpperCase());
                            createGetterSetterMathods(getterSetterBuilder,fieldBuilder,field,"Boolean");
                        } catch (JSONException e3) {
                            try {
                                jsonObject.getString(field);
                                variableBuilder.append("  private String ").append(field).append(";");
                                variableBuilder.append("\n");
                                if(isNeedConstructor) {
                                    constructorBuilder.append("String ").append(field).append(",");
                                    constructorAssignmentBuilder.append("   this.").append(field).append("=").append(field).append(";").append("\n");
                                }
                                StringBuilder fieldBuilder=new StringBuilder(field);
                                String c=fieldBuilder.substring(0,1);
                                fieldBuilder.deleteCharAt(0);
                                fieldBuilder.insert(0,c.toUpperCase());
                                createGetterSetterMathods(getterSetterBuilder,fieldBuilder,field,"String");
                            } catch (JSONException e4) {
                                try {
                                    JSONObject newObject=jsonObject.getJSONObject(field);
                                    StringBuilder fieldBuilder=new StringBuilder(field);
                                    String c=fieldBuilder.substring(0,1);
                                    fieldBuilder.deleteCharAt(0);
                                    fieldBuilder.insert(0,c.toUpperCase());
                                    variableBuilder.append("  private ").append(fieldBuilder).append(" ").append(field).append(";");
                                    variableBuilder.append("\n");
                                    if(isNeedConstructor) {
                                        constructorBuilder.append(fieldBuilder).append(" ").append(field).append(",");
                                        constructorAssignmentBuilder.append("   this.").append(field).append("=").append(field).append(";").append("\n");
                                    }
                                    StringBuilder temp=new StringBuilder("");
                                    StringBuilder imports=new StringBuilder("");
                                    StringBuilder gsTemp=new StringBuilder("");
                                    StringBuilder constructorTemp=new StringBuilder("");
                                    StringBuilder constructorAssignTemp=new StringBuilder("");
                                    createGetterSetterMathods(getterSetterBuilder,fieldBuilder,field,fieldBuilder.toString());
                                    extractVariablesFromJsonObject(newObject,(fieldBuilder.toString()),temp, imports, gsTemp, constructorTemp, constructorAssignTemp, isAnnotationRequired, isNeedConstructor, isGetterSetterRequired);
                                } catch (JSONException e5) {
                                    try {
                                        JSONArray jsonArray=jsonObject.getJSONArray(field);
                                        if(!importsBuilder.toString().contains("List"))
                                        importsBuilder.append("import java.util.List;").append("\n");
                                        StringBuilder fieldBuilder=new StringBuilder(field);
                                        String c=fieldBuilder.substring(0,1);
                                        fieldBuilder.deleteCharAt(0);
                                        fieldBuilder.insert(0,c.toUpperCase());
                                        variableBuilder.append("  private List<").append(fieldBuilder).append("> ").append(field).append(";");
                                        variableBuilder.append("\n");
                                        if(isNeedConstructor) {
                                            constructorBuilder.append("List<").append(fieldBuilder).append("> ").append(field).append(",");
                                            constructorAssignmentBuilder.append("   this.").append(field).append("=").append(field).append(";").append("\n");
                                        }
                                        JSONObject newObject=null;
                                        if(jsonArray.length()>0){
                                            newObject=jsonArray.getJSONObject(0);
                                        }
                                        StringBuilder temp=new StringBuilder("");
                                        StringBuilder imports=new StringBuilder("");
                                        StringBuilder gsTemp=new StringBuilder("");
                                        StringBuilder constructorTemp=new StringBuilder("");
                                        StringBuilder constructorAssignTemp=new StringBuilder("");
                                        createGetterSetterMathods(getterSetterBuilder,fieldBuilder,field,"List<"+(fieldBuilder.toString())+">");
                                        if(newObject!=null)
                                        extractVariablesFromJsonObject(newObject,(fieldBuilder.toString()),temp, imports, gsTemp, constructorTemp, constructorAssignTemp, isAnnotationRequired, isNeedConstructor, isGetterSetterRequired);
                                    } catch (JSONException e6) {
                                        try {
                                            jsonObject.get(field);
                                            variableBuilder.append("  private Object ").append(field).append(";");
                                            variableBuilder.append("\n");
                                            if(isNeedConstructor) {
                                                constructorBuilder.append("Object ").append(field).append(",");
                                                constructorAssignmentBuilder.append("   this.").append(field).append("=").append(field).append(";").append("\n");
                                            }
                                            StringBuilder fieldBuilder=new StringBuilder(field);
                                            String c=fieldBuilder.substring(0,1);
                                            fieldBuilder.deleteCharAt(0);
                                            fieldBuilder.insert(0,c.toUpperCase());
                                            createGetterSetterMathods(getterSetterBuilder,fieldBuilder,field,"Object");
                                        } catch (JSONException e7) {
                                            e7.printStackTrace();
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        try {
            if(bufferedWriter!=null) {
                createFileStucture(name,bufferedWriter,importsBuilder);
                bufferedWriter.write(variableBuilder.toString());
                if(isNeedConstructor) {
                    constructorBuilder.setLength(constructorBuilder.length()-1);
                    constructorBuilder.append("){").append("\n");
                    bufferedWriter.write(constructorBuilder.toString());
                    bufferedWriter.write(constructorAssignmentBuilder.toString());
                    bufferedWriter.write("  }\n");
                }
                if(isGetterSetterRequired) {
                    bufferedWriter.write(getterSetterBuilder.toString());
                }
                bufferedWriter.write("}");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            if(bufferedWriter!=null) {
                bufferedWriter.flush();
                bufferedWriter.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void createGetterSetterMathods(StringBuilder getterSetterBuilder, StringBuilder fieldBuilder, String field,String dataType) {
        getterSetterBuilder.append("  public void set").append(fieldBuilder).append("(").append(dataType).append(" ").append(field)
                .append("){").append("\n").append("   this.").append(field).append("=").append(field).append(";").append("\n").append("  }").append("\n");
        getterSetterBuilder.append("  public ").append(dataType).append(" get").append(fieldBuilder).append("(").append("){")
                .append("\n").append("   return ").append(field).append(";").append("\n").append("  }").append("\n");
    }

    public static void createFileStucture(String name, BufferedWriter bufferedWriter, StringBuilder importsBuilder){
        try {
            String[] splittedPaths=PATH.split("/");
            StringBuilder tempBuilder=new StringBuilder("");
            int tempIndex=0;
            boolean isNeeded=false;
            for(int index=0;index<splittedPaths.length;index++){
                if(splittedPaths[index].contains("java")){
                    isNeeded=true;
                    tempIndex=index+1;
                    break;
                }
            }
            if(!isNeeded){
                tempBuilder.append(splittedPaths[splittedPaths.length-1]);
            }else{
                for(int index=tempIndex;index<splittedPaths.length;index++){
                    if(index!=splittedPaths.length-1) {
                       tempBuilder.append(splittedPaths[index]).append(".");
                    }else{
                        tempBuilder.append(splittedPaths[index]);
                    }
                }
            }
            bufferedWriter.write("package "+tempBuilder+";");
            bufferedWriter.write("\n");
            bufferedWriter.write(importsBuilder.toString());
            bufferedWriter.write("/**\n" +
                    " * Awesome Pojo Generator\n" +
                    " * */\n");
            bufferedWriter.write("public class "+name);
            bufferedWriter.write("{");
            bufferedWriter.write("\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
