package exo2.solution2;

public class Main2s {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
        UseAffichage c = new UseAffichage();
		Affichage TA = new Affichage(c, "AAA");
		Affichage TB = new Affichage(c, "BB");
        Affichage TC = new Affichage(c, "C");
        Affichage TD = new Affichage(c, "DDDD");
        Affichage TE =  new Affichage(c, "EEEEE");
        Affichage TF = new Affichage(c, "FFFFFF");
        Affichage TG = new Affichage(c, "GGGGGGG");
        Affichage TH = new Affichage(c, "HHHHHHHH");

        TA.start();
        TB.start();
        TC.start();
        TD.start();
        TE.start();
        TF.start();
        TG.start();
        TH.start();
	}

}
