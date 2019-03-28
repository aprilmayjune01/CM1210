import java.util.Scanner;
import java.io.File


class RugbyDetails {

    private String name;
    private String id;
    private int tries;
    private String team;
    private String teamid;
    private String stadium;
    private String street;
    private String town;
    private String postcode;


    public RugbyDetails( String inName, String inId, int inTries, String inTeam, String inTeamId, String inStadium, String inStreet, String inTown, String inPostcode ) {
        name = inName;
        id = inId;
        tries = inTries;
        team = inTeam;
        teamid = inTeamId;
        stadium = inStadium;
        street = inStreet;
        town = inTown;
        postcode = inPostcode;

    }

    public String getName( ) {
        return name;
    }

    public String getId( ) {
        return id;
    }

    public int getTries( ) {
        return tries;
    }

    public String getTeam( ) {
        return team;
    }

    public String getTeamId( ) {
        return teamid;
    }

    public String getStadium( ) {
        return stadium;
    }

    public String getStreet( ) {
        return street;
    }

    public String getTown( ) {
        return town;
    }

    public String getPostcode( ) {
        return postcode;
    }






    public void setName( String inName ) {
        name = inNme;
    }

    public void setId( String inId ) {
        id = inId;
    }

    public void setTries( int inTries ) {
        tries = inTries;
    }

    public void setTeam( String inTeam ) {
        team = inTeam;
    }

    public void setTeamId( String inTeamId ) {
        teamid = inTeamId;
    }

    public void setStadium( String inStadium ) {
        stadium = inStadium;
    }

    public void setStreet( String inStreet ) {
        street = inStreet;
    }

    public void setTown( String inTown ) {
        town = inTown;
    }

    public void setPostcode( String inPostcode) {
        postcode = inPostcode;
    }

    public String toString( ) {
        return ( name + "\t" + id + "\t" + tries+ "\t" + team + "\t" + teamid + "\t" + stadium + "\t" + street + "\t" + town + "\t" + postcode);
    }
}

public class AddPlayerDetails {

    private Vector<rugbyDetails> entries;

    public AddPlayerData( ) {
        entries = new Vector<playerEntry>();
    }

    public void add( String playerName, String playerID, int careerTriesScored, String teamName, String teamID, String homeStadiumStreet, String homeStadiumTown, String homeStadiumPostcode ) {
        entries.add(new playerEntry(playerName, playerID, careerTriesScored, teamName, teamID, homeStadiumStreet, homeStadiumTown, homeStadiumPostcode));
    }

    public String toString( ) {
        StringBuffer temp = new StringBuffer();
        for (int i = 0; i < entries.size(); ++i) {
            temp.append( entries.get(i).toString() + "\n" );
        }
        return temp.toString();
    }
}