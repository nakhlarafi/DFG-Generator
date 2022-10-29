package execution;
/*
 * Copyright (c) 2022 by Eyefreight BV (www.eyefreight.com). All rights reserved.
 *
 * This software is provided by the copyright holder and contributors "as is" and any express or implied warranties, including, but
 * not limited to, the implied warranties of merchantability and fitness for a particular purpose are disclaimed. In no event shall
 * Eyefreight BV or contributors be liable for any direct, indirect, incidental, special, exemplary, or consequential damages
 * (including, but not limited to, procurement of substitute goods or services; * loss of use, data, or profits; or business
 * interruption) however caused and on any theory of liability, whether in contract, strict liability, or tort (including
 * negligence or otherwise) arising in any way out of the use of this software, even if advised of the possibility of such damage.
 */

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import facade.JavaDataFlow;
import facade.StaticJavaDataFlow;
import model.DataFlowGraph;
import model.DataFlowMethod;
import model.DataFlowNode;
import org.apache.commons.io.FileUtils;

/**
 * TODO javadoc
 *
 * @author daan.vandenheuvel
 */
public class Main {

  public static void main(String[] args) throws IOException {

    // TODO Change this to the exact location of the project (where the .git file is located).
    String systemPath = "/home/nakhla/Desktop/DataFlow/JavaDataFlowExample";
    System.out.println("================Don't forget to change your project path (" + systemPath + ")================");

    String projectPath = "/JavaDataFlowExample/src/main/java/example/";
    String inputClass = "Example1.java";
    StaticJavaDataFlow.getConfig().setProjectPaths(systemPath + projectPath);
    DataFlowGraph dfg = JavaDataFlow.create(systemPath + projectPath + inputClass);
    System.out.println(dfg);
    final File file = new File("test.txt");
    FileUtils.writeStringToFile(file, dfg.toString(), StandardCharsets.UTF_8);


    System.out.println("================Example getting all inputs for given Node name================");
    String nodeName = "getA";
    DataFlowMethod getA = dfg.getMethods().stream().filter(m -> m.getName().equals(nodeName)).findFirst().get();
    List<DataFlowNode> inputNodes = getA.getReturnNode().get().walkBackUntil(DataFlowNode::isInputParameter, dfg::owns);
    System.out.println(inputNodes.get(0).getName());

    System.out.println("================Printing all fields in the DFG================");
    System.out.println(dfg);

  }

}
