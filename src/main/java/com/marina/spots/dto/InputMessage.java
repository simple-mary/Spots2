package com.marina.spots.dto;

import java.util.List;

/**
 * Created by Marry on 08.10.2016.
 */
public class InputMessage {
    private List<Command> manageCommands;
    private DotDTO dotDTO;
    private String user;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public List<Command> getManageCommands() {
        return manageCommands;
    }

    public void setManageCommands(List<Command> manageCommands) {
        this.manageCommands = manageCommands;
    }

    public DotDTO getDotDTO() {
        return dotDTO;
    }

    public void setDotDTO(DotDTO dotDTO) {
        this.dotDTO = dotDTO;
    }
}
