package com.example.earcengine;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import org.web3j.crypto.Bip32ECKeyPair;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.MnemonicUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.protocol.http.HttpService;
import org.web3j.utils.Convert;

public class page_4 extends AppCompatActivity {
    private String mnemonicS, pass, bal;
    private Bip32ECKeyPair masterKeypair;
     private Web3j web3j;
    private Bip32ECKeyPair derivedKeyPair;
    private Credentials credentials;
    private final int[] derivationPath = {44 | Bip32ECKeyPair.HARDENED_BIT, 60 | Bip32ECKeyPair.HARDENED_BIT, Bip32ECKeyPair.HARDENED_BIT, 0, 0};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page4);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        EditText mnemonics;
        ImageView imageView = findViewById(R.id.prev3);
        mnemonics = findViewById(R.id.entersk);
        LinearLayout import_account;
        import_account = findViewById(R.id.arc_lou);
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


        imageView.setOnClickListener(v -> {
            Intent intent = new Intent(page_4.this, page_3.class);
           intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_NO_ANIMATION);
           startActivity(intent);
        });


        import_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mnemonicS = mnemonics.getText().toString().trim();
                if (mnemonicS.isEmpty()) {
                    Toast.makeText(page_4.this, "Please enter the mnemonic", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        pass = null;

                        // Generate a BIP32 master keypair from the mnemonic phrase
                        masterKeypair = Bip32ECKeyPair.generateKeyPair(MnemonicUtils.generateSeed(mnemonicS, pass));

                        // Derived the key using the derivation path
                        derivedKeyPair = Bip32ECKeyPair.deriveKeyPair(masterKeypair, derivationPath);

                        // Load the wallet for the derived key
                        credentials = Credentials.create(derivedKeyPair);
                        Toast.makeText(page_4.this, credentials.getAddress(), Toast.LENGTH_SHORT).show();
                        try {
                            Credentials credentials = Credentials.create(derivedKeyPair);
                            EthGetBalance ethGetBalance = web3j.ethGetBalance(credentials.getAddress(), DefaultBlockParameterName.LATEST).sendAsync().get();
                            String bal = (Convert.fromWei(ethGetBalance.getBalance().toString(), Convert.Unit.ETHER)).toString();
                            Intent intent = new Intent(page_4.this, page_4_1.class);
                            intent.putExtra("addkey", credentials.getAddress());
                            intent.putExtra("arcbal", bal);
                            intent.putExtra("menmo", mnemonics.getText().toString().trim());
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            startActivity(intent);

                        } catch (Exception e) {
                            Toast.makeText(page_4.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        Toast.makeText(page_4.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }

}
