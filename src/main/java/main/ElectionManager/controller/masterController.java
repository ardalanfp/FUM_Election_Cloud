package main.ElectionManager.controller;

import main.ElectionManager.exception.ElectionNotFoundException;
import main.ElectionManager.model.Election;
import main.ElectionManager.model.ElectionChoice;
import main.ElectionManager.model.ElectionStatistics;
import main.ElectionManager.repository.ElectionChoiceRepository;
import main.ElectionManager.repository.ElectionRepository;
import main.ElectionManager.repository.ElectionStatisticsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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

    @RequestMapping(value = "/election/votes/increment")
    public Map<String,Object> incrementElectionTotalVoteCount(){
        Map<String,Object> map = new HashMap<>();
        if(!counterDataBaseRead){
            int c = electionStatisticsRepository.findById(1).get().getCounterColoumn();
            ElectionStatistics.setCounter(c);
            counterDataBaseRead = true;
        }

        ElectionStatistics.incrementVoteCounter();
        electionStatisticsRepository.save(new ElectionStatistics(ElectionStatistics.getCounter()));
        map.put("message","successful");
        return map;
    }



    @RequestMapping(value = "/election/exists")
    public Map<String,Object> ElectionExists(int electionId){
        Map<String,Object> map = new HashMap<>();
        System.out.println(electionId);
        if(!electionRepository.existsById(electionId)){
          throw new ElectionNotFoundException();
        }
        map.put("message","successful");
        return map;
    }


    @RequestMapping(value = "/election/get")
    public Map<String,Object> getElectionDetails(int electionId){
        Map<String,Object> map = new HashMap<>();
        Optional<Election> e =  electionRepository.findById(electionId);
        if(e.isPresent()){
            map.put("data",e.get());
            map.put("message","successful");
            return map;
        }
        throw new ElectionNotFoundException();
    }

    @RequestMapping(value = "/election/all")
    public Map<String,Object> getListOfElections(){
        Map<String,Object> map = new HashMap<>();
        map.put("data",electionRepository.findAll());
        map.put("message","successful");
        return map;
    }


    @RequestMapping(value = "/election/save", method = RequestMethod.POST)
    public Map<String,Object> saveElection(@RequestBody Election e) {
        Map<String,Object> map = new HashMap<>();
        validateElection(e);
        electionRepository.save(e);
        map.put("message","successful");
        return map;
    }


    @RequestMapping(value = "/election/remove")
    public Map<String,Object> removeElection(int electionId){
        Map<String,Object> map = new HashMap<>();
        electionRepository.deleteById(electionId);
        map.put("message","successful");
        return map;
    }


    @RequestMapping(value = "/election/update", method = RequestMethod.PUT)
    public Map<String,Object> editElection(@RequestBody Election election){
        Map<String,Object> map = new HashMap<>();
        validateElection(election);
        electionRepository.save(election);
        map.put("message","successful");
        return map;
    }


    @RequestMapping(value = "/election/choices")
    public Map<String,Object> getListOfChoices(int electionId){
        Map<String,Object> map = new HashMap<>();
        if (electionRepository.findById(electionId) == null){
            throw  new InvalidParameterException();
        }
        map.put("data",electionChoiceRepository.findByElectionId(electionId));
        map.put("message","successful");
        return map;
    }


    @RequestMapping(value = "/test")
    public Map<String,Object>  test(){
        Map<String,Object> map = new HashMap<>();
        Date d = new Date();
        map.put("message",d);
        map.put("elec",new Election("N1",new Date(),new Date()));
        return map;
    }
}
