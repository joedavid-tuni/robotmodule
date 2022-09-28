import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.http.entity.ContentType;

import java.io.IOException;
import java.util.*;


public class RobotController {

    static String IP = "192.168.1.115";
    static String port = "3002";

    static String pfx_camo = "https://joedavid91.github.io/ontologies/camo/product#";
    static String pfx_cm = "http://resourcedescription.tut.fi/ontology/capabilityModel#";
    static String pfx_xsd = "http://www.w3.org/2001/XMLSchema#";
    static String pfx_owl = "http://www.w3.org/2002/07/owl#";
    static Queue<ActionObject> aol = new LinkedList<ActionObject>();

    static String currentPlan = new String();

    static ArrayList<String> completedPlans = new ArrayList<>();

    static ArrayList<String> removedAOs = new ArrayList<>();
    static ArrayList<String> knownAOs = new ArrayList<>();

    static Table<String, String,  ArrayList<String>> lookupTable
            = HashBasedTable.create();

    static TestSocket sock;

    private static void populateLookUpTable() {


        ArrayList<String> EngineBlockBase_ProcessTask_Setup = new ArrayList<>(
                List.of("movej([-1.7071962225543427, -1.5707724730121058, -1.5707533995257776, -1.5707963267948966, -4.726985756550924, -0.0370410124408167], a=1.3962634015954636, v=1.0471975511965976)"
                ));

        ArrayList<String> EngineRods_Picking = new ArrayList<>(
                Arrays.asList("movej([-1.7071962225543427, -1.5707724730121058, -1.5707533995257776, -1.5707963267948966, -4.726985756550924, -0.0370410124408167], a=1.3962634015954636, v=1.0471975511965976)",
                        "movej([-1.7071962225543427, -1.5707724730121058, -1.5707533995257776, -1.5707963267948966, -4.726985756550924, -0.0370410124408167], a=1.3962634015954636, v=1.0471975511965976)",
                        "movej([-1.7071962225543427, -1.5707724730121058, -1.5707533995257776, -1.5707963267948966, -4.726985756550924, -0.0370410124408167], a=1.3962634015954636, v=1.0471975511965976)"
                ));

        ArrayList<String> EngineRods_Placing = new ArrayList<>(
                Arrays.asList("movej([-1.7071962225543427, -1.5707724730121058, -1.5707533995257776, -1.5707963267948966, -4.726985756550924, -0.0370410124408167], a=1.3962634015954636, v=1.0471975511965976)",
                        "movej([-1.7071962225543427, -1.5707724730121058, -1.5707533995257776, -1.5707963267948966, -4.726985756550924, -0.0370410124408167], a=1.3962634015954636, v=1.0471975511965976)",
                        "movej([-1.7071962225543427, -1.5707724730121058, -1.5707533995257776, -1.5707963267948966, -4.726985756550924, -0.0370410124408167], a=1.3962634015954636, v=1.0471975511965976)"
                ));

        ArrayList<String> EngineBlockFrame_Picking = new ArrayList<>(
                Arrays.asList("movej([-1.7071962225543427, -1.5707724730121058, -1.5707533995257776, -1.5707963267948966, -4.726985756550924, -0.0370410124408167], a=1.3962634015954636, v=1.0471975511965976)",
                        "movej([-1.7071962225543427, -1.5707724730121058, -1.5707533995257776, -1.5707963267948966, -4.726985756550924, -0.0370410124408167], a=1.3962634015954636, v=1.0471975511965976)",
                        "movej([-1.7071962225543427, -1.5707724730121058, -1.5707533995257776, -1.5707963267948966, -4.726985756550924, -0.0370410124408167], a=1.3962634015954636, v=1.0471975511965976)"
                ));

        ArrayList<String> EngineBlockFrame_Placing = new ArrayList<>(
                Arrays.asList("movej([-1.7071962225543427, -1.5707724730121058, -1.5707533995257776, -1.5707963267948966, -4.726985756550924, -0.0370410124408167], a=1.3962634015954636, v=1.0471975511965976)",
                        "movej([-1.7071962225543427, -1.5707724730121058, -1.5707533995257776, -1.5707963267948966, -4.726985756550924, -0.0370410124408167], a=1.3962634015954636, v=1.0471975511965976)",
                        "movej([-1.7071962225543427, -1.5707724730121058, -1.5707533995257776, -1.5707963267948966, -4.726985756550924, -0.0370410124408167], a=1.3962634015954636, v=1.0471975511965976)"
                ));

        ArrayList<String> RockerArm_Picking = new ArrayList<>(
                Arrays.asList("movej([-1.7071962225543427, -1.5707724730121058, -1.5707533995257776, -1.5707963267948966, -4.726985756550924, -0.0370410124408167], a=1.3962634015954636, v=1.0471975511965976)",
                        "movej([-1.7071962225543427, -1.5707724730121058, -1.5707533995257776, -1.5707963267948966, -4.726985756550924, -0.0370410124408167], a=1.3962634015954636, v=1.0471975511965976)",
                        "movej([-1.7071962225543427, -1.5707724730121058, -1.5707533995257776, -1.5707963267948966, -4.726985756550924, -0.0370410124408167], a=1.3962634015954636, v=1.0471975511965976)"
                ));

        ArrayList<String> RockerArm_Placing = new ArrayList<>(
                Arrays.asList("movej([-1.7071962225543427, -1.5707724730121058, -1.5707533995257776, -1.5707963267948966, -4.726985756550924, -0.0370410124408167], a=1.3962634015954636, v=1.0471975511965976)",
                        "movej([-1.7071962225543427, -1.5707724730121058, -1.5707533995257776, -1.5707963267948966, -4.726985756550924, -0.0370410124408167], a=1.3962634015954636, v=1.0471975511965976)",
                        "movej([-1.7071962225543427, -1.5707724730121058, -1.5707533995257776, -1.5707963267948966, -4.726985756550924, -0.0370410124408167], a=1.3962634015954636, v=1.0471975511965976)"
                ));

        ArrayList<String> RockerArmShaft_Picking= new ArrayList<>(
                Arrays.asList("movej([-1.7071962225543427, -1.5707724730121058, -1.5707533995257776, -1.5707963267948966, -4.726985756550924, -0.0370410124408167], a=1.3962634015954636, v=1.0471975511965976)",
                        "movej([-1.7071962225543427, -1.5707724730121058, -1.5707533995257776, -1.5707963267948966, -4.726985756550924, -0.0370410124408167], a=1.3962634015954636, v=1.0471975511965976)",
                        "movej([-1.7071962225543427, -1.5707724730121058, -1.5707533995257776, -1.5707963267948966, -4.726985756550924, -0.0370410124408167], a=1.3962634015954636, v=1.0471975511965976)"
                ));

        ArrayList<String> RockerArmShaft_Picking_Moving_Approach = new ArrayList<>(
                List.of("movej([-1.7071962225543427, -1.5707724730121058, -1.5707533995257776, -1.5707963267948966, -4.726985756550924, -0.0370410124408167], a=1.3962634015954636, v=1.0471975511965976)"
                ));
        ArrayList<String> RockerArmShaft_Picking_Moving_Extract = new ArrayList<>(
                List.of("movej([-1.7071962225543427, -1.5707724730121058, -1.5707533995257776, -1.5707963267948966, -4.726985756550924, -0.0370410124408167], a=1.3962634015954636, v=1.0471975511965976)"
                ));
        ArrayList<String> RockerArmShaft_PPicking_FingerGrasping_Grasping = new ArrayList<>(
                List.of("movej([-1.7071962225543427, -1.5707724730121058, -1.5707533995257776, -1.5707963267948966, -4.726985756550924, -0.0370410124408167], a=1.3962634015954636, v=1.0471975511965976)"
                ));

        ArrayList<String> RockerArmShaft_Placing_Moving_Approach = new ArrayList<>(
                List.of("movej([-1.7071962225543427, -1.5707724730121058, -1.5707533995257776, -1.5707963267948966, -4.726985756550924, -0.0370410124408167], a=1.3962634015954636, v=1.0471975511965976)"
                ));
        ArrayList<String> RockerArmShaft_Placing_Moving_Extract = new ArrayList<>(
                List.of("movej([-1.7071962225543427, -1.5707724730121058, -1.5707533995257776, -1.5707963267948966, -4.726985756550924, -0.0370410124408167], a=1.3962634015954636, v=1.0471975511965976)"
                ));
        ArrayList<String> RockerArmShaft_Placing_FingerGrasping_Brace = new ArrayList<>(
                List.of("movej([-1.7071962225543427, -1.5707724730121058, -1.5707533995257776, -1.5707963267948966, -4.726985756550924, -0.0370410124408167], a=1.3962634015954636, v=1.0471975511965976)"
                ));
        ArrayList<String> RockerArmShaft_PPlacing_Releasing_Release = new ArrayList<>(
                List.of("movej([-1.7071962225543427, -1.5707724730121058, -1.5707533995257776, -1.5707963267948966, -4.726985756550924, -0.0370410124408167], a=1.3962634015954636, v=1.0471975511965976)"
                ));


        lookupTable.put("EngineBlockBase","ProcessTask", EngineBlockBase_ProcessTask_Setup);

        // v: processClass_targetCapability_context(sh:name)
        // v: ArrayList<String> with three actions
        lookupTable.put("EngineRods","Picking",EngineRods_Picking);
        lookupTable.put("EngineRods","Placing",EngineRods_Placing);

        // v: ArrayList<String> with three actions
        lookupTable.put("EngineBlockFrame","Picking",EngineBlockFrame_Picking);
        lookupTable.put("EngineBlockFrame","Placing",EngineBlockFrame_Placing);

        // v: ArrayList<String> with three actions
        lookupTable.put("RockerArm","Picking", RockerArm_Picking);
        lookupTable.put("RockerArm","Placing",RockerArm_Placing);

        // v: ArrayList<String> with one actions
        lookupTable.put("RockerArmShaft","Picking",RockerArmShaft_Picking);
//        lookupTable.put("RockerArmShaft","Picking", RockerArmShaft_Picking_Moving_Extract);
//        lookupTable.put("RockerArmShaft","Picking",RockerArmShaft_PPicking_FingerGrasping_Grasping);

        // v: ArrayList<String> with one actions
        lookupTable.put("RockerArmShaft","Placing_Moving_Approach", RockerArmShaft_Placing_Moving_Approach);
        lookupTable.put("RockerArmShaft","Placing_Moving_Extract", RockerArmShaft_Placing_Moving_Extract);
        lookupTable.put("RockerArmShaft","Placing_FingerGrasping_Brace", RockerArmShaft_Placing_FingerGrasping_Brace);
        lookupTable.put("RockerArmShaft","Placing_Releasing_Release", RockerArmShaft_PPlacing_Releasing_Release);


    }


    public static String queryRobotKB(String query){
        RequestSpecification requestSpecification = RestAssured.given().contentType(ContentType.APPLICATION_FORM_URLENCODED.toString()).param("query", query);
        Response response = requestSpecification.post("http://"+ IP + ":" + port + "/ds/query");

        return response.asString();
    }

    public static void updateKBOfTaskExecution(String actionName){
        String query = " PREFIX camo:<"+pfx_camo+"> PREFIX xsd:<"+pfx_xsd+">" +
                "DELETE { camo:"+actionName+" camo:hasStatus \"initial\"^^xsd:string.}" +
                "INSERT { camo:"+actionName+" camo:hasStatus \"executed\"^^xsd:string.}" +
                "WHERE { camo:"+actionName+" camo:hasStatus ?status.}";
        RequestSpecification requestSpecification = RestAssured.given().contentType(ContentType.APPLICATION_FORM_URLENCODED.toString()).param("update", query);
        Response response = requestSpecification.post("http://"+ IP + ":" + port + "/ds/update");
        System.out.println(response.asString());

    }

    public static void executeAction(String name, String part, String execID) throws IOException, InterruptedException {

        System.out.println("Looking to execute action: " + execID + " for part " + part);
        ArrayList<String> tcpCommand = lookupTable.get(part,execID);

        if(tcpCommand !=null) {
            System.out.println("Sending Command.." + tcpCommand);
//            sock.sendMessage(tcpCommand);
            // todo: update kb with the specific execution as "peforming"
            Thread.sleep(3000);
            removedAOs.add(aol.peek().getExecID());
            aol.remove();
            updateKBOfTaskExecution(name);
        }
        else{
            System.out.println("Failed looking up action table for action: " + execID);

            //temporarily here to mimic completion of execution

        }
    }

    public static void executePlan(String part, String processClass) throws InterruptedException {
        System.out.println("Looking to execute actions of Plan for a " + processClass + " process for part " + part);
        ArrayList<String> tcpCommands = lookupTable.get(part,processClass);
        //todo: update all actions of plan as executed
        if(tcpCommands !=null) {
            for (String tcpCommand : tcpCommands) {
                System.out.println("Command Found. Sending Command.." + tcpCommand);
                //            sock.sendMessage(tcpCommand);

                Thread.sleep(1000);
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
          String query = "PREFIX camo:<https://joedavid91.github.io/ontologies/camo/product#> " +
                  "SELECT ?activePlan ?planType WHERE {" +
                  " GRAPH <https://joedavid91.github.io/ontologies/camo-named-graph/intention> " +
                  "{ ?activePlan a camo:ActivePlan. } " +
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
              System.out.println("ActivePlan: " + currentPlan + ", Plan Type: "  + activePlanType);
//          }

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
                              " ?place camo:includesActivities ?process." +
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

                          ActionObject ao = new ActionObject(name, performer, status, order, execID, part, UID);

                          if (!(removedAOs.contains(execID) || knownAOs.contains(execID))) { // if previously executed action
                              aol.add(ao);
                              knownAOs.add(execID);
                          }
                      }

                      if (aol.size() > 0) {


                          ActionObject curr_ao = aol.peek();
                          System.out.println("Current AO: " + curr_ao.getName() + " " + curr_ao.getExecID() + " " + curr_ao.getPart());
                          ;

                          // todo: if(!robot is executing last motion)

                          switch (Objects.requireNonNull(curr_ao).getHasStatus()) {

                              case "initial":

                                  if (Objects.equals(curr_ao.getIsPerformedBy(), "robot")) {

                                      executeAction(curr_ao.getName(), curr_ao.getPart(), curr_ao.getExecID()); // assumtpion is that it has to have an encoded action, this will always be successfull

                                  }
                                  else if (Objects.equals(curr_ao.getIsPerformedBy(), "operator")) {

                                  System.out.println("Waiting for Operator to perform");

                              }
                                  break;

                              case "executed":

                                  removedAOs.add(aol.peek().getExecID());
                                  aol.remove();

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
                              " ?place camo:includesActivities ?process." +
                              " ?process camo:isPerformedOnProductComponent ?part. " +
                              " ?process a ?processClass. " +
                              " FILTER (!sameTerm(?processClass, owl:NamedIndividual))"+
                              " }";
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

    public static void main(String[] args) throws InterruptedException, IOException {
        sock = new TestSocket("127.0.0.1", 30002);
        sock.startConnection();
        populateLookUpTable();
        while(true) {
            // do something
            // pause to avoid churning
            runController();
            Thread.sleep( 1000 );
        }


//        System.out.println(ContentType.APPLICATION_FORM_URLENCODED.get());



    }


}
