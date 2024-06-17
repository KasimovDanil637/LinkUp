package web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.model.Invites;
import web.model.User;
import web.repositories.InvitesRepository;

import java.util.List;

@Service
public class InviteService {
    @Autowired
    private InvitesRepository invitesRepository;

    public List<Invites> findInvitesUserNotAccepted(User user) { return invitesRepository.findAllByUserNotAccepted(user); }
    public void saveInvite(User user, User friend) {
        Invites invites = new Invites();
        invites.setFriend(user);
        invites.setUser(friend);
        invitesRepository.save(invites);
    }
    public void updateInvite(User user, User friend){
        Invites invites = findByUserAndFriend(user, friend);
        invites.setAccepted(true);
    }
    public Invites findByUserAndFriend(User user, User friend){ return invitesRepository.findByUserAndFriend(user, friend);}
}
