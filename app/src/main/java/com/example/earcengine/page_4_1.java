package com.example.earcengine;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import org.web3j.crypto.Bip32ECKeyPair;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.MnemonicUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.Transfer;
import org.web3j.utils.Convert;

import java.math.BigDecimal;

public class page_4_1 extends AppCompatActivity {
    private Bip32ECKeyPair derivedKeyPair;
    private Bip32ECKeyPair masterKeypair;
    private String mnemonicS, pass, bal;
    private final int[] derivationPath = {44 | Bip32ECKeyPair.HARDENED_BIT, 60 | Bip32ECKeyPair.HARDENED_BIT, Bip32ECKeyPair.HARDENED_BIT, 0, 0};
    private Credentials credentials;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page41);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        TextView textView = findViewById(R.id.arcadd);
        Button transfer = findViewById(R.id.send_ether);
        TextView textView1 = findViewById(R.id.arcbal);
        EditText to = findViewById(R.id.to);
        EditText amount = findViewById(R.id.amount);
        ImageView connect = findViewById(R.id.connect);
        Web3j web3j = Web3j.build(new HttpService("https://goerli.infura.io/v3/ea7458e8af8c4da89b45d4b88ab38831"));
        try {
            Web3ClientVersion clientVersion = web3j.web3ClientVersion().sendAsync().get();
            if (!clientVersion.hasError()) {

                Toast.makeText(this, "Connected", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, clientVersion.getError().getMessage(), Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }


        Intent intent = getIntent();
        // receive the value by getStringExtra() method and
        // key must be same which is send by first activity
        String str = intent.getStringExtra("addkey");
        String bal = intent.getStringExtra("arcbal");
        String menmo = intent.getStringExtra("menmo");
        // display the string into textView
        textView.setText(str);
        textView1.setText(bal + " ETH");

        pass = null;

        // Generate a BIP32 master keypair from the mnemonic phrase
        masterKeypair = Bip32ECKeyPair.generateKeyPair(MnemonicUtils.generateSeed(menmo, pass));

        // Derived the key using the derivation path
        derivedKeyPair = Bip32ECKeyPair.deriveKeyPair(masterKeypair, derivationPath);

        // Load the wallet for the derived key
        credentials = Credentials.create(derivedKeyPair);


        transfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Credentials credentials = Credentials.create(derivedKeyPair);
                    double amt = Double.parseDouble(amount.getText().toString().trim());
                    String to1 = to.getText().toString();
                    TransactionReceipt transactionReceipt = Transfer.sendFunds(web3j, credentials, to1, BigDecimal.valueOf(amt), Convert.Unit.ETHER).sendAsync().get();
                    Toast.makeText(page_4_1.this, transactionReceipt.getStatus(),Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(page_4_1.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }



}