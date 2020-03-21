package pers.dreamer.service;

import pers.dreamer.bean.Invitation;

public interface InvitationService {
    public boolean findInvitCode(String invitecode);

    public  boolean updateInvitCode(String invitcode);

    void insertInvitation(Invitation invitation);
}
