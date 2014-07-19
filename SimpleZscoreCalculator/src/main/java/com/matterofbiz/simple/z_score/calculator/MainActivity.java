package com.matterofbiz.simple.z_score.calculator;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import org.apache.commons.math.MathException;

public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppRater.app_launched(this);
    }


    public void calculateZ(View button) throws MathException {

        double TA;
        TA = 0;
        double CA;
        CA = 0;
        double TD;
        TD = 0;
        double CD;
        CD = 0;
        double EQ;
        double RE;
        RE = 0;
        double EB;
        EB = 0;
        double SL;
        SL = 0;
        double PBT;
        PBT = 0;
        double INV;
        INV = 0;
        double DEP;
        DEP = 0;
        String Ztype;
        double zscore;
        double zpd;
        String zmsg;
        double X1;
        X1 = 0;
        double X2;
        X2 = 0;
        double X3;
        X3 = 0;
        double X4;
        X4 = 0;
        double X5;
        X5 = 0;
        double X6;
        X6 = 0;
        double X7;
        X7 = 0;
        double X8;
        X8 = 0;
        double X9;
        X9 = 0.000001;

        // get the references to the widgets
        EditText TAtxt = (EditText) findViewById(R.id.Edit_TotAssets);
        EditText CAtxt = (EditText) findViewById(R.id.Edit_CurAssets);
        EditText INVtxt = (EditText) findViewById(R.id.Edit_INV);
        EditText TDtxt = (EditText) findViewById(R.id.Edit_TotDebt);
        EditText CDtxt = (EditText) findViewById(R.id.Edit_CurDebt);
        EditText REtxt = (EditText) findViewById(R.id.Edit_RetEar);
        EditText EBtxt = (EditText) findViewById(R.id.Edit_Ebit);
        EditText PBTtxt = (EditText) findViewById(R.id.Edit_PBT);
        EditText SLtxt = (EditText) findViewById(R.id.Edit_Sales);
        EditText DEPtxt = (EditText) findViewById(R.id.Edit_Depn);
        Spinner typespn = (Spinner) findViewById(R.id.type);
        String msgZ;

        // get the users values from the widget references
        if (TAtxt.getText().length() > 0) {
            TA = Float.parseFloat(TAtxt.getText().toString());
        }
        if (CAtxt.getText().length() > 0) {
            CA = Float.parseFloat(CAtxt.getText().toString());
        }
        if (INVtxt.getText().length() > 0) {
            INV = Float.parseFloat(INVtxt.getText().toString());
        }
        if (TDtxt.getText().length() > 0) {
            TD = Float.parseFloat(TDtxt.getText().toString());
        }
        if (CDtxt.getText().length() > 0) {
            CD = Float.parseFloat(CDtxt.getText().toString());
        }
        if (REtxt.getText().length() > 0) {
            RE = Float.parseFloat(REtxt.getText().toString());
        }
        if (EBtxt.getText().length() > 0) {
            EB = Float.parseFloat(EBtxt.getText().toString());
        }
        if (PBTtxt.getText().length() > 0) {
            PBT = Float.parseFloat(PBTtxt.getText().toString());
        }
        if (SLtxt.getText().length() > 0) {
            SL = Float.parseFloat(SLtxt.getText().toString());
        }
        if (DEPtxt.getText().length() > 0) {
            DEP = Float.parseFloat(DEPtxt.getText().toString());
        }

        Ztype = typespn.getSelectedItem().toString();


        //Calculate financial ratios
        if(TA != 0){
            X1 = (CA-CD)/TA;
            X2 = RE/TA;
            X3 = EB/TA;
            X5 = SL/TA;
            X8 = CD/TA;
        }

        if(TD != 0){
            EQ = (TA - TD);
            X4 = EQ/TD;
        }

        if(CD != 0){
            X6 = PBT/CD;
        }

        if(TD != 0){
            X7 = CA/TD;
        }

        if((SL-PBT-DEP) != 0){
            X9 = (CA-INV-CD)/((SL-PBT-DEP)/365);
        }

        // calculate Z-score according to Alman(1968),
        if (Ztype.equals("Public/Quoted Company")) {
            zscore = 1.2*X1+1.4*X2+3.3*X3+0.6*X4+1.0*X5;
            if (zscore < 1.81){
                zmsg = " (Warning!)";
            }else{
                if (zscore < 2.99){
                    zmsg = " (Unclear)";
                }else{
                    zmsg = " (Safe)";
                }
            }
        }else{ // Altman (...),
            if (Ztype.equals("Private Company")) {
                zscore = 0.717*X1+0.847*X2+3.107*X3+0.42*X4+0.998*X5;
                if (zscore < 1.23){
                    zmsg = " (Warning!)";
                }else{
                    if (zscore < 2.90){
                        zmsg = " (Unclear)";
                    }else{
                        zmsg = " (Safe)";
                    }
                }
            }else{ // and Altman (1995).
                if (Ztype.equals("Non-manufacturing")) {
                    zscore = 6.56*X1+3.26*X2+6.72*X3+1.05*X4;
                    if (zscore < 1.10){
                        zmsg = " (Warning!)";
                    }else{
                        if (zscore < 2.60){
                            zmsg = " (Unclear)";
                        }else{
                            zmsg = " (Safe)";
                        }
                    }
                }else{
                    zscore = 3.20+12.18*X6+2.50*X7+10.68*X8+0.029*X9;
                    if (zscore < 0){
                        zmsg = " (Warning!)";
                    }else{
                        zmsg = " (Safe)";
                    }
                }
            }
        }

        zpd = (1-(Math.exp(zscore)/(1+Math.exp(zscore))))*100;
        msgZ = "Z-score: " + String.format("%.2f", zscore) + zmsg +"\nProbability of default is " + String.format("%.2f", zpd) + "%";


        // dialog...
        new AlertDialog.Builder(this)
                .setTitle("Results").setMessage(msgZ)
                .setPositiveButton(R.string.helpok,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // TODO Auto-generated method stub
                            }
                        }
                )
                .show();

    }

}





