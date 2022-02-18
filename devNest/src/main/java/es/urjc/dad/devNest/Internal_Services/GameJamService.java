package es.urjc.dad.devNest.Internal_Services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.urjc.dad.devNest.Database.Entities.GamejamEntity;
import es.urjc.dad.devNest.Database.Entities.UserEntity;
import es.urjc.dad.devNest.Database.Repositories.GamejamRepository;

@Service
public class GameJamService {
    @Autowired
    private GamejamRepository gamejamRepository;

    private List<GamejamEntity> allJams;
    private boolean needsUpdate;

    public GameJamService()
    {
        needsUpdate = true;
    }    

    public void refreshJamList()
    {
        allJams = gamejamRepository.findAll();
        needsUpdate = false;
    }

    public List<GamejamEntity> getAllJams()
    {
        if(needsUpdate)
        {
            refreshJamList();
        }
        return allJams;
    }

    public boolean addNewJam(String _name, UserEntity _userEntity, String _topic, Date _startDate, Date _endDate)
    {
        GamejamEntity newJam = new GamejamEntity(_name, _userEntity, _topic, _startDate, _endDate);
        Optional<GamejamEntity> u = gamejamRepository.findById(newJam.getId());

        if(!u.isPresent())
        {
            gamejamRepository.save(newJam);
            needsUpdate = true;
            return true; 
        }

        return false;
    } 
}
