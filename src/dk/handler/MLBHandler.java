package dk.handler;

import dk.data.Draft;
import dk.data.Player;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

/**
 * This class handles the MLB Teams screen
 *
 * @author Matthew Wong
 */
public class MLBHandler {
    //constants
    public final String ATLANTA = "ATL";
    public final String ARIZONA = "AZ";
    public final String CHICAGO = "CHC";
    public final String CINCINNATI = "CIN";
    public final String COLORADO = "COL";
    public final String LOS_ANGELES = "LAD";
    public final String MIAMI = "MIA";
    public final String MILWAUKEE = "MIL";
    public final String NEW_YORK = "NYM";
    public final String PHILADELPHIA = "PHI";
    public final String PITTSBURGH = "PIT";
    public final String SAN_DIEGO = "SD";
    public final String SAN_FRANCISCO = "SF";
    public final String ST_LOUIS = "STL";
    public final String WASHINGTON = "WAS";
    
    ObservableList<Player> totPlayers;
    
    ObservableList<Player> AtlantaList;
    ObservableList<Player> ArizonaList;
    ObservableList<Player> ChicagoList;
    ObservableList<Player> CincinnatiList;
    ObservableList<Player> ColoradoList;
    ObservableList<Player> LosAngelesList;
    ObservableList<Player> MiamiList;
    ObservableList<Player> MilwaukeeList;
    ObservableList<Player> NewYorkList;
    ObservableList<Player> PhiladelphiaList;
    ObservableList<Player> PittsburghList;
    ObservableList<Player> SanDiegoList;
    ObservableList<Player> SanFranciscoList;
    ObservableList<Player> StLouisList;
    ObservableList<Player> WashingtonList;
    
    public MLBHandler(Draft draft) {
        totPlayers = FXCollections.observableArrayList();
        
        AtlantaList = FXCollections.observableArrayList();
        ArizonaList = FXCollections.observableArrayList();
        ChicagoList = FXCollections.observableArrayList();
        CincinnatiList = FXCollections.observableArrayList();
        ColoradoList = FXCollections.observableArrayList();
        LosAngelesList = FXCollections.observableArrayList();
        MiamiList = FXCollections.observableArrayList();
        MilwaukeeList = FXCollections.observableArrayList();
        NewYorkList = FXCollections.observableArrayList();
        PhiladelphiaList = FXCollections.observableArrayList();
        PittsburghList = FXCollections.observableArrayList();
        SanDiegoList = FXCollections.observableArrayList();
        SanFranciscoList = FXCollections.observableArrayList();
        StLouisList = FXCollections.observableArrayList();
        WashingtonList = FXCollections.observableArrayList();
        
        initLists(draft);
    }
    
    public void initLists(Draft draft) {
        totPlayers.clear();
        totPlayers = draft.getPlayers();
        
        for (Player p : totPlayers) {
            if (p.getProTeam().equals(ATLANTA))
                AtlantaList.add(p);
            else if (p.getProTeam().equals(ARIZONA))
                ArizonaList.add(p);
            else if (p.getProTeam().equals(CHICAGO))
                ChicagoList.add(p);
            else if (p.getProTeam().equals(CINCINNATI))
                CincinnatiList.add(p);
            else if (p.getProTeam().equals(COLORADO))
                ColoradoList.add(p);
            else if (p.getProTeam().equals(LOS_ANGELES))
                LosAngelesList.add(p);
            else if (p.getProTeam().equals(MIAMI))
                MiamiList.add(p);
            else if (p.getProTeam().equals(MILWAUKEE))
                MilwaukeeList.add(p);
            else if (p.getProTeam().equals(NEW_YORK))
                NewYorkList.add(p);
            else if (p.getProTeam().equals(PHILADELPHIA))
                PhiladelphiaList.add(p);
            else if (p.getProTeam().equals(PITTSBURGH))
                PittsburghList.add(p);
            else if (p.getProTeam().equals(SAN_DIEGO))
                SanDiegoList.add(p);
            else if (p.getProTeam().equals(SAN_FRANCISCO))
                SanFranciscoList.add(p);
            else if (p.getProTeam().equals(ST_LOUIS))
                StLouisList.add(p);
            else
                WashingtonList.add(p);
        }
    }
    
    public ObservableList<Player> getAtlantaTeamList() {
        return AtlantaList;
    }
    
    public void handleTeamChangeRequest(TableView<Player> table, String selection) {
        if (selection.equals(ATLANTA))
            table.setItems(AtlantaList);
        else if (selection.equals(ARIZONA))
            table.setItems(ArizonaList);
        else if (selection.equals(CHICAGO))
            table.setItems(ChicagoList);
        else if (selection.equals(CINCINNATI))
            table.setItems(CincinnatiList);
        else if (selection.equals(COLORADO))
            table.setItems(ColoradoList);
        else if (selection.equals(LOS_ANGELES))
            table.setItems(LosAngelesList);
        else if (selection.equals(MIAMI))
            table.setItems(MiamiList);
        else if (selection.equals(MILWAUKEE))
            table.setItems(MilwaukeeList);
        else if (selection.equals(NEW_YORK))
            table.setItems(NewYorkList);
        else if (selection.equals(PHILADELPHIA))
            table.setItems(PhiladelphiaList);
        else if (selection.equals(PITTSBURGH))
            table.setItems(PittsburghList);
        else if (selection.equals(SAN_DIEGO))
            table.setItems(SanDiegoList);
        else if (selection.equals(SAN_FRANCISCO))
            table.setItems(SanFranciscoList);
        else if (selection.equals(ST_LOUIS))
            table.setItems(StLouisList);
        else
            table.setItems(WashingtonList);
    }
}
