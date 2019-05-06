package main.ElectionManager.controller;

import main.ElectionManager.exception.ElectionChoiceNotFound;
import main.ElectionManager.exception.ElectionNotFoundException;
import main.ElectionManager.model.Election;
import main.ElectionManager.model.ElectionChoice;
import main.ElectionManager.model.ElectionStatistics;
import main.ElectionManager.repository.ElectionChoiceRepository;
import main.ElectionManager.repository.ElectionRepository;
import main.ElectionManager.repository.ElectionStatisticsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.InvalidParameterException;
import java.util.*;


@RestController
public class masterController {


    @Autowired
    ElectionRepository electionRepository;

    @Autowired
    ElectionChoiceRepository electionChoiceRepository;

    @Autowired
    ElectionStatisticsRepository electionStatisticsRepository;

    private static boolean counterDataBaseRead = false;

    private void validateElection(Election e){
        if (e.getEndTime() == null || e.getStartTime() == null || e.getName()==null || "".equals(e.getName())){
            throw new InvalidParameterException("Invalid parameter exception");
        }
    }

    @RequestMapping(value = "/elections/votes/increment")
    public Map<String,Object> incrementElectionTotalVoteCount(){
        Map<String,Object> map = new HashMap<>();
        if(!counterDataBaseRead){
            ElectionStatistics es;
            try {
                 es = electionStatisticsRepository.findById(1).get();
            }
            catch (Exception e) {
                es = new ElectionStatistics();
            }

            int c = es.getCounterColoumn();
            ElectionStatistics.setCounter(c);
            counterDataBaseRead = true;
        }

        ElectionStatistics.incrementVoteCounter();
        electionStatisticsRepository.save(new ElectionStatistics(ElectionStatistics.getCounter()));
        map.put("message","successful");
        return map;
    }



    @RequestMapping(value = "/elections/exists")
    public Map<String,Object> ElectionExists(int electionId){
        Map<String,Object> map = new HashMap<>();
        System.out.println(electionId);
        if(!electionRepository.existsById(electionId)){
          throw new ElectionNotFoundException();
        }
        map.put("message","successful");
        return map;
    }


    @RequestMapping(value = "/elections/{electionId}/get")
    public Map<String,Object> getElectionDetails(@PathVariable (value = "electionId") int electionId){
        Map<String,Object> map = new HashMap<>();
        Optional<Election> e =  electionRepository.findById(electionId);
        if(e.isPresent()){
            map.put("data",e.get());
            map.put("message","successful");
            return map;
        }
        throw new ElectionNotFoundException();
    }

    @RequestMapping(value = "/elections/all")
    public Map<String,Object> getListOfElections(){
        Map<String,Object> map = new HashMap<>();
        map.put("data",electionRepository.findAll());
        map.put("message","successful");
        return map;
    }


    @RequestMapping(value = "/elections/save", method = RequestMethod.POST)
    public Map<String,Object> saveElection(@RequestBody Election e) {
        Map<String,Object> map = new HashMap<>();
        validateElection(e);
        electionRepository.save(e);
        map.put("message","successful");
        return map;
    }


    @RequestMapping(value = "/elections/remove")
    public Map<String,Object> removeElection(int electionId){
        Map<String,Object> map = new HashMap<>();
        electionRepository.deleteById(electionId);
        map.put("message","successful");
        return map;
    }


    @RequestMapping(value = "/elections/update", method = RequestMethod.PUT)
    public Map<String,Object> editElection(@RequestBody Election election){
        Map<String,Object> map = new HashMap<>();
        validateElection(election);
        if(! electionRepository.findById(election.getId()).isPresent()){
            throw  new InvalidParameterException();
        }
        electionRepository.save(election);
        map.put("message","successful");
        return map;
    }


    @RequestMapping(value = "/elections/{electionId}/choices/all")
    public Map<String,Object> getListOfChoices(@PathVariable (value = "electionId") int electionId){
        Map<String,Object> map = new HashMap<>();
        if (! electionRepository.findById(electionId).isPresent()){
            throw  new InvalidParameterException();
        }
        map.put("data",electionChoiceRepository.findByElectionId(electionId));
        map.put("message","successful");
        return map;
    }

    private void validateChoice(ElectionChoice choice) {
        if (choice.getChoice() == null || choice.getChoice().equals("")){
            System.out.println("invalid choice given.");
            throw  new InvalidParameterException();
        }
    }


    @RequestMapping(value = "election/{electionId}/choices/save",method = RequestMethod.POST)
    public Map<String,Object> createElectionChoice(@PathVariable (value = "electionId") int electionId,
                                                   @RequestBody ElectionChoice choice){
        Map<String,Object> map = new HashMap<>();
        validateChoice(choice);
        return electionRepository.findById(electionId).map(election -> {
            choice.setElection(election);
            electionChoiceRepository.save(choice);
            map.put("message","successful");
            return map;
        }).orElseThrow(() -> new ElectionNotFoundException());
    }


    @RequestMapping(value = "election/{electionId}/choices/{electionChoiceId}/remove")
    public Map<String,Object> removeElectionChoice(
            @PathVariable (value = "electionId") int electionId,
            @PathVariable (value = "electionChoiceId") int electionChoiceId){
        Map<String,Object> map = new HashMap<>();
        if(!electionRepository.existsById(electionId)) {
            throw new ElectionNotFoundException();
        }

        electionChoiceRepository.deleteById(electionChoiceId);
        map.put("message","successful");
        return map;
    }


    @RequestMapping(value = "election/{electionId}/choices/{electionChoiceId}/edit",method = RequestMethod.PUT)
    public Map<String,Object> editElectionChoice(
            @PathVariable (value = "electionId") int electionId,
            @PathVariable (value = "electionChoiceId") int electionChoiceId,
            @RequestBody ElectionChoice choice){
        Map<String,Object> map = new HashMap<>();
        validateChoice(choice);

        if(!electionRepository.existsById(electionId)) {
            throw new ElectionNotFoundException();
        }

        return electionChoiceRepository.findById(electionChoiceId).map(c -> {
            c.setChoice(choice.getChoice());
            electionChoiceRepository.save(c);
            map.put("message","successful");
            return map;
        }).orElseThrow(() -> new ElectionChoiceNotFound());


    }




    @RequestMapping(value = "/heartbeat")
    public void  heartbeat(){
    }
}
