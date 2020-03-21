package pers.dreamer.service.impls;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pers.dreamer.bean.Invitation;
import pers.dreamer.bean.InvitationExample;
import pers.dreamer.dao.InvitationMapper;
import pers.dreamer.service.InvitationService;

@Service
public class InvitationServiceImpl implements InvitationService {

    @Autowired
    InvitationMapper invitationMapper;

    @Override
    public boolean findInvitCode(String invitecode) {
        InvitationExample invitationExample =   new InvitationExample();
        invitationExample.createCriteria().andInvitecodeEqualTo(invitecode).andStateEqualTo(0);
        return invitationMapper.countByExample(invitationExample) > 0;
    }

    @Transactional
    @Override
    public boolean updateInvitCode(String invitecode) {
        InvitationExample invitationExample =  new InvitationExample();
        invitationExample.createCriteria().andInvitecodeEqualTo(invitecode);
        Invitation invitation = new Invitation();
        invitation.setState(1);
        return invitationMapper.updateByExampleSelective(invitation,invitationExample) > 0;
    }

    @Override
    public void insertInvitation(Invitation invitation) {
        invitationMapper.insertSelective(invitation);
    }
}
