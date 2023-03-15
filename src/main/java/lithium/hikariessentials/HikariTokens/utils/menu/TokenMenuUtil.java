package lithium.hikariessentials.HikariTokens.utils.menu;

import org.bukkit.entity.Player;

public class TokenMenuUtil {


    private Player owner;

    public TokenMenuUtil(Player owner) {
        this.owner = owner;
    }

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }


}



