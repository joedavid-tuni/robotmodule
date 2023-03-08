import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.http.entity.ContentType;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;


public class RobotController {

//    static String IP = "192.168.1.115";
    static String IP = "localhost";
    static String port = "3002";

    static String fileName = "/home/robolab/IdeaProjects/RobotModule/src/main/java/script.txt";

    static String data = "";

    static String pfx_camo = "https://joedavid-tuni.github.io/ontologies/camo#";
    static String pfx_cm = "http://resourcedescription.tut.fi/ontology/capabilityModel#";
    static String pfx_xsd = "http://www.w3.org/2001/XMLSchema#";
    static String pfx_owl = "http://www.w3.org/2002/07/owl#";
    static LinkedList<ActionObject> aol = new LinkedList<ActionObject>();

    static String currentPlan = new String();

    static String dataStart = "";
    static String dataEnd = "";

    static ArrayList<String> completedPlans = new ArrayList<>();


    static ArrayList<String> removedAOs = new ArrayList<>();

    static ArrayList<String> removedAOs_ready = new ArrayList<>();
    static ArrayList<String> knownAOs = new ArrayList<>();

    static String awaitingAOExecID = new String();

    static Table<String, String,  ArrayList<String>> lookupTable
            = HashBasedTable.create();

    static TestSocket sock;

    private static void populateLookUpTable() {




//        ArrayList<String> EngineRods_Picking = new ArrayList<>(
//                Arrays.asList("movej([-0.534726345669899, -0.9137987753107089, -2.243677557582947, -1.5334700835407808, -4.746426096781267, 1.063893278703966], a=1.3962634015954636, v=1.0471975511965976)",
//                        "movel([-0.5330122169333151, -0.9585688905137246, -2.462047931079354, -1.2702649762208242, -4.745783857322062, 1.0618727516691364], a=1.2, v=0.25)",
//                        "RG2(57,40,5.0,True,False,False)",
//                        "movel([-0.536278541391404, -0.9685246652510298, -1.8587611364372352, -1.8637537994898299, -4.746137637349669, 1.0669113689564977], a=1.2, v=0.25)"
//                ));
//
//        ArrayList<String> EngineRods_Placing = new ArrayList<>(
//                Arrays.asList("movej([-1.7071962225543427, -1.5707724730121058, -1.5707533995257776, -1.5707963267948966, -4.726985756550924, -0.0370410124408167], a=1.3962634015954636, v=1.0471975511965976)",
//                        "movej([-1.7071962225543427, -1.5707724730121058, -1.5707533995257776, -1.5707963267948966, -4.726985756550924, -0.0370410124408167], a=1.3962634015954636, v=1.0471975511965976)",
//                        "movej([-1.7071962225543427, -1.5707724730121058, -1.5707533995257776, -1.5707963267948966, -4.726985756550924, -0.0370410124408167], a=1.3962634015954636, v=1.0471975511965976)"
//                ));


//        ArrayList<String> RockerArm_Picking = new ArrayList<>(
//                Arrays.asList("movej([-1.7071962225543427, -1.5707724730121058, -1.5707533995257776, -1.5707963267948966, -4.726985756550924, -0.0370410124408167], a=1.3962634015954636, v=1.0471975511965976)",
//                        "movej([-1.7071962225543427, -1.5707724730121058, -1.5707533995257776, -1.5707963267948966, -4.726985756550924, -0.0370410124408167], a=1.3962634015954636, v=1.0471975511965976)",
//                        "movej([-1.7071962225543427, -1.5707724730121058, -1.5707533995257776, -1.5707963267948966, -4.726985756550924, -0.0370410124408167], a=1.3962634015954636, v=1.0471975511965976)"
//                ));
//
//        ArrayList<String> RockerArm_Placing = new ArrayList<>(
//                Arrays.asList("movej([-1.7071962225543427, -1.5707724730121058, -1.5707533995257776, -1.5707963267948966, -4.726985756550924, -0.0370410124408167], a=1.3962634015954636, v=1.0471975511965976)",
//                        "movej([-1.7071962225543427, -1.5707724730121058, -1.5707533995257776, -1.5707963267948966, -4.726985756550924, -0.0370410124408167], a=1.3962634015954636, v=1.0471975511965976)",
//                        "movej([-1.7071962225543427, -1.5707724730121058, -1.5707533995257776, -1.5707963267948966, -4.726985756550924, -0.0370410124408167], a=1.3962634015954636, v=1.0471975511965976)"
//                ));

        //DONE



//        ArrayList<String> RockerArmShaft_Picking_Moving_Extract = new ArrayList<>(
//                List.of("movej([-1.7071962225543427, -1.5707724730121058, -1.5707533995257776, -1.5707963267948966, -4.726985756550924, -0.0370410124408167], a=1.3962634015954636, v=1.0471975511965976)"
//                ));
//        ArrayList<String> RockerArmShaft_PPicking_FingerGrasping_Grasping = new ArrayList<>(
//                List.of("movej([-1.7071962225543427, -1.5707724730121058, -1.5707533995257776, -1.5707963267948966, -4.726985756550924, -0.0370410124408167], a=1.3962634015954636, v=1.0471975511965976)"
//                ));
//

        ArrayList<String> EngineBlockBase_ProcessTask_Setup = new ArrayList<>(
                // Open Gripper and Move to Home Position
                List.of(getGripperCommand("RG2(100,40,0.0,True,False,False)"),
                        "movej([-1.5708463827716272, -1.5708563963519495, -1.5707772413836878, -1.5708444754229944, -4.713188885237159, 0.03688444942235947], a=1.3962634015954636, v=1.0471975511965976)"
                ));

        ArrayList<String> EngineBlockFrame_Picking = new ArrayList<>(
                Arrays.asList(" movej([-0.5364811647714589, -0.9980501649603646, -1.7514481229415946, -1.9415645240941686, -4.745890057875578, 1.0677156487609474], a=1.3962634015954636, v=1.0471975511965976)", //"Aprch_Frame_PCK"
                        " movel([-0.5330122169333151, -0.9585688905137246, -2.462047931079354, -1.2702649762208242, -4.745783857322062, 1.0618727516691364], a=1.2, v=0.25)",  //"Grasp_Frame_PCK"
                        getGripperCommand("RG2(57,40,5.0,True,False,False)"),
                        "movel([-0.536278541391404, -0.9685246652510298, -1.8587611364372352, -1.8637537994898299, -4.746137637349669, 1.0669113689564977], a=1.2, v=0.25)" //"Deprt_Frame_PCK"
                ));

        ArrayList<String> EngineBlockFrame_Placing = new ArrayList<>(
                Arrays.asList(" movej([-0.2503000510618989, -1.454741324028836, -1.5299543937799953, -1.6895570085369442, -4.720705673638335, 1.3422504579181036], a=1.3962634015954636, v=1.0471975511965976)", //"Aprch_Frame_PLA"
                        "  movel([-0.24955827391875118, -1.460555378467605, -1.8677538071676034, -1.345933263153789, -4.720267121041752, 1.338127136214339], a=1.2, v=0.25)", //"Rel_Frame_PLA"
                        getGripperCommand("RG2(100,40,0.0,True,False,False)"),
                        " movel([-0.2503000510618989, -1.454741324028836, -1.5299543937799953, -1.6895570085369442, -4.720705673638335, 1.3422504579181036], a=1.2, v=0.25)" ,//"Deprt_Frame_PLA"
                        "movej([-0.38375017931779354, -1.1307486361255767, -1.7556651855186303, -1.7890880816018964, -4.71486288908199, 1.211119658003656], a=1.2, v=0.25)" //"intclr"
                ));

        ArrayList<String> RockerArmShaft_Picking= new ArrayList<>(
                Arrays.asList("movej([-0.12710231996092958, -2.35165055119397, -0.3995129969324065, -1.9489377440747617, -4.753657470342383, 1.4478076144738836], a=1.3962634015954636, v=1.0471975511965976)", // "Aprch_Shaft_PCK"
                        "movel([-0.12654837845146716, -2.282831900514048, -1.0502501197688972, -1.3667555235377584, -4.754097559973442, 1.4403813749549401], a=1.2, v=0.25)", // "Grasp_Shaft_PCK"
                        getGripperCommand("RG2(56,40,0.0,True,False,False)"),
                        "movel([-0.1271178977212415, -2.3982237113387654, -0.26878658614444007, -2.0331632992613944, -4.753226408853108, 1.4488291904862456], a=1.2, v=0.25)", //"Deprt_Shaft_PCK"
                        "movel([-0.20298723428986065, -1.5904602568491377, -1.3705969472672068, -1.7369752488428194, -4.7535097017395564, 1.3713097592351873], a=1.2, v=0.25)", // "Aprch_Shaft_PLA" to bring to the position for freedrive
                        "movel([-0.20288754861238623, -1.5779914889137938, -1.4958193073336528, -1.6242283674558937, -4.753645175974084, 1.3699212705712878], a=1.2, v=0.25)" // staying away from message
                ));




        ArrayList<String> RockerArmShaft_Placing_FingerGrasping_Brace = new ArrayList<>(
                List.of(getGripperCommand("")) // todo: check if this is needed
        );

        ArrayList<String> RockerArmShaft_Placing_Moving_Approach_ready = new ArrayList<>(
                List.of(getGripperCommand(dataStart)
                ));

        ArrayList<String> RockerArmShaft_Placing_Moving_Approach_executed = new ArrayList<>(
                List.of(getGripperCommand(dataEnd)
                ));

        ArrayList<String> RockerArmShaft_Placing_Releasing_Release = new ArrayList<>(
                List.of("movej([-0.2023453276224645, -1.5875174935203642, -1.7083852441089205, -1.4020538538031753, -4.753291489934603, 1.3671810075629904], a=1.2, v=0.25)", //"Rel_Shaft_PLA" needed to reorient after manual guidance,
                 getGripperCommand("RG2(78,40,0.0,True,False,False)"))
        );

        ArrayList<String> RockerArmShaft_Placing_Moving_Extract = new ArrayList<>(
                List.of("  movel([-0.20290724767717805, -1.5891104609179507, -1.3804507807415787, -1.7285380912856407, -4.753515896790468, 1.3712406172096687], a=1.2, v=0.25)") // "Deprt_Shaft_PLA"
        );




        // v: processClass_targetCapability_context(sh:name)
        // v: ArrayList<String> with three actions
//        lookupTable.put("EngineRods","Picking",EngineRods_Picking);
//        lookupTable.put("EngineRods","Placing",EngineRods_Placing);

//        lookupTable.put("Screws","Screwing",EngineRods_Placing);

        // v: ArrayList<String> with three actions


        // v: ArrayList<String> with three actions
//        lookupTable.put("RockerArm1","Picking", RockerArm_Picking);
//        lookupTable.put("RockerArm1","Placing",RockerArm_Placing);
//        lookupTable.put("RockerArm2","Picking", RockerArm_Picking);
//        lookupTable.put("RockerArm2","Placing",RockerArm_Placing);
//        lookupTable.put("RockerArm3","Picking", RockerArm_Picking);
//        lookupTable.put("RockerArm3","Placing",RockerArm_Placing);
//        lookupTable.put("RockerArm4","Picking", RockerArm_Picking);
//        lookupTable.put("RockerArm4","Placing",RockerArm_Placing);
//        lookupTable.put("RockerArm5","Picking", RockerArm_Picking);
//        lookupTable.put("RockerArm5","Placing",RockerArm_Placing);
//        lookupTable.put("RockerArm6","Picking", RockerArm_Picking);
//        lookupTable.put("RockerArm6","Placing",RockerArm_Placing);
//        lookupTable.put("RockerArm7","Picking", RockerArm_Picking);
//        lookupTable.put("RockerArm7","Placing",RockerArm_Placing);
//        lookupTable.put("RockerArm8","Picking", RockerArm_Picking);
//        lookupTable.put("RockerArm8","Placing",RockerArm_Placing);
        // v: ArrayList<String> with one actions

//        lookupTable.put("RockerArmShaft","Picking", RockerArmShaft_Picking_Moving_Extract);
//        lookupTable.put("RockerArmShaft","Picking",RockerArmShaft_PPicking_FingerGrasping_Grasping);

        // v: ArrayList<String> with one actions
        lookupTable.put("EngineBlockBase","Setup", EngineBlockBase_ProcessTask_Setup);

        lookupTable.put("EngineBlockFrame","Picking",EngineBlockFrame_Picking);
        lookupTable.put("EngineBlockFrame","Placing",EngineBlockFrame_Placing);

        lookupTable.put("RockerArmShaft","Picking",RockerArmShaft_Picking);

        lookupTable.put("RockerArmShaft","Placing_FingerGrasping_Brace_initial", RockerArmShaft_Placing_FingerGrasping_Brace);
        lookupTable.put("RockerArmShaft","Placing_Moving_Approach_ready", RockerArmShaft_Placing_Moving_Approach_ready);
        lookupTable.put("RockerArmShaft","Placing_Moving_Approach_executed", RockerArmShaft_Placing_Moving_Approach_executed);
        lookupTable.put("RockerArmShaft","Placing_Releasing_Release_initial", RockerArmShaft_Placing_Releasing_Release);
        lookupTable.put("RockerArmShaft","Placing_Moving_Extract_initial", RockerArmShaft_Placing_Moving_Extract);


    }


    public static String queryRobotKB(String query){
        RequestSpecification requestSpecification = RestAssured.given().contentType(ContentType.APPLICATION_FORM_URLENCODED.toString()).param("query", query);
        Response response = requestSpecification.post("http://"+ IP + ":" + port + "/ds/query");

        return response.asString();
    }

    public static void updateKBOfActionExecution(String actionName){
        String query = " PREFIX camo:<"+pfx_camo+"> PREFIX xsd:<"+pfx_xsd+">" +
                "DELETE { camo:"+actionName+" camo:hasStatus \"initial\"^^xsd:string.}" +
                "INSERT { camo:"+actionName+" camo:hasStatus \"executed\"^^xsd:string.}" +
                "WHERE { camo:"+actionName+" camo:hasStatus ?status.}";
        RequestSpecification requestSpecification = RestAssured.given().contentType(ContentType.APPLICATION_FORM_URLENCODED.toString()).param("update", query);
        Response response = requestSpecification.post("http://"+ IP + ":" + port + "/ds/update");
        System.out.println(response.asString());

    }

    public static void executeAction(String name, String part, String execID, String status) throws IOException, InterruptedException {

        System.out.println("Looking to execute action: " + execID + " for part " + part + " for status " + status);
        ArrayList<String> tcpCommands = lookupTable.get(part,execID+"_"+status);

        if(tcpCommands !=null) {

            for(String tcpCommand: tcpCommands) {
                System.out.println("Sending Command.." + tcpCommand);

//                sock.sendMessage(tcpCommand);//todo: temp remove while debugging
            }
            // todo: update kb with the specific execution as "peforming"
            Thread.sleep(2200);
            if(!Objects.equals(status, "ready")) {
                removedAOs.add(aol.peek().getExecID());
                aol.remove();
                updateKBOfActionExecution(name);
            }
            else{
                // todo: somehow make sure actions readied are not done again
                removedAOs_ready.add(aol.peek().getExecID()+"_"+status);
            }
        }
        else{
            System.out.println("Failed looking up action table for action: " + execID);

            //temporarily here to mimic completion of execution

        }
    }

    public static void executePlan(String part, String processClass) throws InterruptedException, IOException {
        System.out.println("Looking to execute actions of Plan for a " + processClass + " process for part " + part);
        ArrayList<String> tcpCommands = lookupTable.get(part,processClass);
        //todo: update all actions of plan as executed
        if(tcpCommands !=null) {
            for (String tcpCommand : tcpCommands) {
                System.out.println("Command Found. Sending Command.." + tcpCommand);
//                sock.sendMessage(tcpCommand); //todo: temp remove while debugging

                Thread.sleep(2200);
            }
            updateKBOfIndividualPlanExecution();
            completedPlans.add(currentPlan);
        }
        else{
            System.out.println("Failed looking up action table");

        }

        //todo: update all actions of plan as executed

    }

    private static void updateKBOfIndividualPlanExecution() {
        // Update all entailed actions of a plan as executed
        System.out.println("Updating " + currentPlan + " as executed.");
        String query = " PREFIX camo:<"+pfx_camo+"> PREFIX xsd:<"+pfx_xsd+">" +
                "DELETE { ?action camo:hasStatus \"initial\"^^xsd:string.}" +
                "INSERT { ?action camo:hasStatus \"executed\"^^xsd:string.}" +
                "WHERE {  ?action camo:isPartOfPlan camo:" + currentPlan + "." +
                "?action camo:hasStatus ?status.}";
        RequestSpecification requestSpecification = RestAssured.given().contentType(ContentType.APPLICATION_FORM_URLENCODED.toString()).param("update", query);
        Response response = requestSpecification.post("http://"+ IP + ":" + port + "/ds/update");
        System.out.println(response.asString());
    }

    public static String getLocalName(String URI){
        String[] arr = URI.split("#");
        String localName = arr[arr.length - 1];

        return localName;
    }


      public static void runController() throws IOException, InterruptedException {


          //  get the current plan
          String query = "PREFIX camo:<" + pfx_camo + "> " +
                  "SELECT ?activePlan ?planType WHERE {" +
                  " GRAPH <https://joedavid91.github.io/ontologies/camo-named-graph/intention> " +
                  "{ ?activePlan a camo:AvailablePlan. } " +
                  "{ ?activePlan camo:hasType ?planType. } " +
                  "}";
          String responseString = queryRobotKB(query);
//          System.out.println(responseString);
          JsonPath jp = new JsonPath(responseString).setRootPath("results");
          String activePlan_withURI = jp.getString("bindings[0].activePlan.value");
          String activePlanType = jp.getString("bindings[0].planType.value");


          //          if(!Objects.equals(currentPlan, extractedPlan)){
          if (activePlan_withURI != null) {
              currentPlan = getLocalName(activePlan_withURI);
              System.out.println("\n\nActivePlan: " + currentPlan + ", Plan Type: "  + activePlanType);
//          }
//              if((Objects.equals(currentPlan, "AP_PT_PickRockerArm"))||(Objects.equals(currentPlan, "AP_PT_PlaceRockerArm"))){
//                  Thread.sleep(20000);
//              }

              if (!completedPlans.contains(currentPlan)) {
                  // get the actions of the current plan and store them as action objects

                  if(Objects.equals(activePlanType, "collaborative")) {
                      String query1 = "PREFIX camo:<" + pfx_camo + "> PREFIX owl:<" + pfx_owl + "> " +
                              "SELECT  ?action ?status ?performer ?order ?capClass ?part ?UID  ?execID WHERE {" +
                              " ?action camo:isPartOfPlan camo:" + currentPlan + "." +
                              " ?action camo:isPerformedBy ?performer. " +
                              " ?action camo:hasStatus ?status. " +
                              " ?action camo:order ?order. " +
                              " ?action camo:hasCapabilityClass ?capClass. " +
                              " ?action camo:UID ?UID. " +
                              " ?action camo:hasExecutionIdentifier ?execID." +
                              " camo:" + currentPlan + " camo:hasHead ?place." +
                              " ?place camo:includesActivity ?process." +
                              " ?process camo:isPerformedOnProductComponent ?part. " +
                              " }" +
                              "ORDER BY ASC(?order)";
                      String responseString1 = queryRobotKB(query1);
//            System.out.println(responseString1);

                      Integer lengthOfResult = JsonPath.from(responseString1).getList("results.bindings").size();
                      System.out.println("length of result " + lengthOfResult);

                      for (int i = 0; i < lengthOfResult; i++) {
                          String propertyPath = "results.bindings[" + i + "]";
                          String name = getLocalName(JsonPath.from(responseString1).getString(propertyPath + ".action.value"));
                          String status = JsonPath.from(responseString1).getString(propertyPath + ".status.value");
                          String performer = JsonPath.from(responseString1).getString(propertyPath + ".performer.value");
                          Integer order = JsonPath.from(responseString1).getInt(propertyPath + ".order.value");
                          String execID = JsonPath.from(responseString1).getString(propertyPath + ".execID.value");
                          String part = getLocalName(JsonPath.from(responseString1).getString(propertyPath + ".part.value"));
                          String UID = getLocalName(JsonPath.from(responseString1).getString(propertyPath + ".UID.value"));


                          System.out.println(status + " " + performer + " " + order + " " + execID + " " + part + " " + UID);
                          System.out.println("Awaiting " + awaitingAOExecID);

                          if(Objects.equals(execID, awaitingAOExecID) && !Objects.equals(status, "initial")){ // if there is a newly executed action by the operator
                            aol.remove();
                            ActionObject ao = new ActionObject(name, performer, status, order, execID, part, UID);
                            aol.add(0,ao);
                            if(Objects.equals(status, "executed")) {
                                awaitingAOExecID = "";
                            }
                          }

                        //todo: if(Objects.equals(execID, awaitingAOExecID) && Objects.equals(status, "performing")){

                          if (!(removedAOs.contains(execID) || knownAOs.contains(execID))) { // if not previously executed or previously known action
                              ActionObject ao = new ActionObject(name, performer, status, order, execID, part, UID);
                              aol.add(ao);
                              knownAOs.add(execID);
                          }
                      }

                      if (aol.size() > 0) {


                          ActionObject curr_ao = aol.peek();
                          System.out.println("Current AO: " + curr_ao.getName() + " " + curr_ao.getExecID() + " " +curr_ao.getIsPerformedBy() + " " + curr_ao.getPart() + " " + curr_ao.getHasStatus());

                          // todo: if(!robot is executing last motion)

                          switch (Objects.requireNonNull(curr_ao).getHasStatus()) {

                              case "initial":

                                  if (Objects.equals(curr_ao.getIsPerformedBy(), "robot")) {

                                      executeAction(curr_ao.getName(), curr_ao.getPart(), curr_ao.getExecID(), curr_ao.getHasStatus()); // assumtpion is that it has to have an encoded action, this will always be successfull

                                  }
                                  else if (Objects.equals(curr_ao.getIsPerformedBy(), "operator")) {

                                      awaitingAOExecID = curr_ao.getExecID();

                                  System.out.println("Waiting for Operator to perform");

                              } else if (Objects.equals(curr_ao.getIsPerformedBy(), "operator,robot")) {
                                      awaitingAOExecID = curr_ao.getExecID();
                                      System.out.println("Waiting for Operator to get ready and inform");


                                  }
                                  break;

                              case "ready":

                                  if(!removedAOs_ready.contains(curr_ao.getExecID()+"_"+curr_ao.getHasStatus())) {

                                      executeAction(curr_ao.getName(), curr_ao.getPart(), curr_ao.getExecID(), curr_ao.getHasStatus());
                                  }

                                  break;

                              case "executed":

                                  executeAction(curr_ao.getName(), curr_ao.getPart(), curr_ao.getExecID(), curr_ao.getHasStatus());

                                  removedAOs.add(aol.peek().getExecID());

                                  if(Objects.equals(curr_ao.getExecID(), aol.peek().getExecID())) {
                                      aol.remove();
                                  }
                                  break;

                          }
                      } else { // plan completely executed
                          completedPlans.add(currentPlan);
                          System.out.println("Plan " + currentPlan + " completely executed");
                          System.out.println("Waiting until information system automatically recognizes this and fires transition to obtain new plan... ");

                      }
                  } else if (Objects.equals(activePlanType, "individual")) {

                      String query1 = "PREFIX camo:<" + pfx_camo + "> PREFIX owl:<" + pfx_owl + "> " +
                              "SELECT  ?part ?processClass  WHERE {" +
                              " camo:" + currentPlan + " camo:hasHead ?place." +
                              " ?place camo:includesActivity ?process." +
                              " ?process camo:isPerformedOnProductComponent ?part. " +
                              " ?process a ?processClass. " +
                              " FILTER (!sameTerm(?processClass, owl:NamedIndividual))"+
                              " FILTER (!sameTerm(?processClass, owl:Class))"+
                              " }";
                      System.out.println(query1);
                      String responseString1 = queryRobotKB(query1);
//            System.out.println(responseString1);

                      String part = getLocalName(JsonPath.from(responseString1).getString("results.bindings[0].part.value"));
                      String processClass = getLocalName(JsonPath.from(responseString1).getString("results.bindings[0].processClass.value"));

                      executePlan(part, processClass);

                  }
              }
              else{
                  System.out.println("Completed Plans " + completedPlans);
                  System.out.println("Already an executed Plan");
              }


//          for(Map<String, Object> binding: bindings){
//              Map<String,String> map = binding.get("performer");
////              ActionObject ao = new ActionObject(binding.get("performer").
//          }

              // todo: for each of the objects in asc order wait till the actions are executed if not the robot's responsibility or execute if it is by TCP commands of hardcoded robot motion


          }
          else {
              System.out.println("No active Plans. Current Plan is " + activePlan_withURI);
          }
      }

      public static String  getGripperCommand(String RGcommand){
        String command = data + RGcommand+"\nend";
        return command;
      }

    public static void main(String[] args) throws InterruptedException, IOException {

        sock = new TestSocket("192.168.1.200", 30002);
        data = new String(Files.readAllBytes(Paths.get(fileName)));

        dataStart = new String(Files.readAllBytes(Paths.get("/home/robolab/IdeaProjects/RobotModule/src/main/java/startForceMode.txt")));
        dataEnd = new String(Files.readAllBytes(Paths.get("/home/robolab/IdeaProjects/RobotModule/src/main/java/endForceMode.txt")));
        String data = "";




//        sock.startConnection(); //todo: temp remove while debugging
//        sock.startConnection(); //todo: temp remove while debugging
        populateLookUpTable();
        while(true) {
            // do something
            // pause to avoid churning
            runController();
            Thread.sleep( 1000 );
        }
//        String command1 = getGripperCommand(dataStart);
//        sock.sendMessage(command1);

//        String command2 = getGripperCommand(dataEnd);
//        sock.sendMessage(command2);
//        sock.sendMessage (getGripperCommand(data2));
//        System.out.println("Done");


//        System.out.println(ContentType.APPLICATION_FORM_URLENCODED.get());



    }


}
