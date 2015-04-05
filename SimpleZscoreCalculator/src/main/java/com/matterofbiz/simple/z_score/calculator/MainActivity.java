package com.matterofbiz.simple.z_score.calculator;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Spinner;

import org.apache.commons.math.MathException;

import static android.widget.Spinner.*;

public class MainActivity extends Activity implements OnItemSelectedListener {

    Spinner spinnertype;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppRater.app_launched(this);
        spinnertype=(Spinner)findViewById(R.id.type);
        spinnertype.setOnItemSelectedListener(this);

        // Get all grouped UI elements
        findViewsById();
    }

    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        int zModel;
        zModel = spinnertype.getSelectedItemPosition();
        // Toast.makeText(getApplicationContext(), String.valueOf(zModel), Toast.LENGTH_LONG).show();

        if (zModel == 3){
            ui4.txtINV.setVisibility(VISIBLE);
            ui4.INVtxt.setVisibility(VISIBLE);
            ui4.txtPBT.setVisibility(VISIBLE);
            ui4.PBTtxt.setVisibility(VISIBLE);
            ui4.txtSL.setVisibility(VISIBLE);
            ui4.SLtxt.setVisibility(VISIBLE);
            ui4.txtDEP.setVisibility(VISIBLE);
            ui4.DEPtxt.setVisibility(VISIBLE);
        }else{
            ui4.txtINV.setVisibility(View.GONE);
            ui4.INVtxt.setVisibility(View.GONE);
            ui4.txtPBT.setVisibility(View.GONE);
            ui4.PBTtxt.setVisibility(View.GONE);
            ui4.txtSL.setVisibility(View.GONE);
            ui4.SLtxt.setVisibility(View.GONE);
            ui4.txtDEP.setVisibility(View.GONE);
            ui4.DEPtxt.setVisibility(View.GONE);
        }
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Do nothing
    }

    class UItype4 {
        TextView txtINV;
        EditText INVtxt;
        TextView txtPBT;
        EditText PBTtxt;
        TextView txtSL;
        EditText SLtxt;
        TextView txtDEP;
        EditText DEPtxt;
    }
    UItype4 ui4 = new UItype4();

    private void findViewsById(){
        ui4.txtINV = (TextView) findViewById (R.id.txtINV);
        ui4.INVtxt = (EditText) findViewById(R.id.Edit_INV);
        ui4.txtPBT = (TextView) findViewById (R.id.txtPBT);
        ui4.PBTtxt = (EditText) findViewById(R.id.Edit_PBT);
        ui4.txtSL = (TextView) findViewById (R.id.txtSales);
        ui4.SLtxt = (EditText) findViewById(R.id.Edit_Sales);
        ui4.txtDEP = (TextView) findViewById (R.id.txtDepn);
        ui4.DEPtxt = (EditText) findViewById(R.id.Edit_Depn);
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
        EditText TDtxt = (EditText) findViewById(R.id.Edit_TotDebt);
        EditText CDtxt = (EditText) findViewById(R.id.Edit_CurDebt);
        EditText REtxt = (EditText) findViewById(R.id.Edit_RetEar);
        EditText EBtxt = (EditText) findViewById(R.id.Edit_Ebit);

        Spinner typespn = (Spinner) findViewById(R.id.type);
        String msgZ;

        // get the users values from the widget references
        if (TAtxt.getText().length() > 0) {
            TA = Float.parseFloat(TAtxt.getText().toString());
        }
        if (CAtxt.getText().length() > 0) {
            CA = Float.parseFloat(CAtxt.getText().toString());
        }
        if (ui4.INVtxt.getText().length() > 0) {
            INV = Float.parseFloat(ui4.INVtxt.getText().toString());
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
        if (ui4.PBTtxt.getText().length() > 0) {
            PBT = Float.parseFloat(ui4.PBTtxt.getText().toString());
        }
        if (ui4.SLtxt.getText().length() > 0) {
            SL = Float.parseFloat(ui4.SLtxt.getText().toString());
        }
        if (ui4.DEPtxt.getText().length() > 0) {
            DEP = Float.parseFloat(ui4.DEPtxt.getText().toString());
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





