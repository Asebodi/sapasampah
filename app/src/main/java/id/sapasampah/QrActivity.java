package id.sapasampah;

import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.os.Bundle;
import android.os.Environment;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

public class QrActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    ImageView qrImageView;
    ConstraintLayout downloadLayout;
    Bitmap bitmap;
    public final static int QRCodeWidth = 500;
    private static final String IMAGE_DIRECTORY = "/QRCodeDownloaded";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr);

        Toolbar toolbar = findViewById(R.id.qrToolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        qrImageView = findViewById(R.id.qrImageView);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            try {
                String uid = user.getUid();
                bitmap = TextToImageEncode(uid);
                qrImageView.setImageBitmap(bitmap);
            } catch (WriterException e) {
                e.printStackTrace();
            }
        }

        downloadLayout = findViewById(R.id.downloadLayout);
        downloadLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String path = saveImage(bitmap);
                Toast.makeText(QrActivity.this, "QR code telah diunduh di " + path, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private Bitmap TextToImageEncode(String Value) throws WriterException {
        BitMatrix bitMatrix;
        try {
            bitMatrix = new MultiFormatWriter().encode(
                    Value,
                    BarcodeFormat.DATA_MATRIX.QR_CODE,
                    QRCodeWidth, QRCodeWidth, null
            );
        } catch (IllegalArgumentException illegalArgumentException) {
            return null;
        }

        int bitMatrixWidth = bitMatrix.getWidth();
        int bitMatrixHeight = bitMatrix.getHeight();
        int[] pixels = new int[bitMatrixWidth * bitMatrixHeight];

        for (int y = 0; y < bitMatrixHeight; y++) {
            int offset = y * bitMatrixWidth;

            for (int x = 0; x < bitMatrixWidth; x++) {
                pixels[offset + x] = bitMatrix.get(x, y) ? getResources().getColor(R.color.qrBlack) : getResources().getColor(R.color.qrWhite);
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(bitMatrixWidth, bitMatrixHeight, Bitmap.Config.ARGB_4444);
        bitmap.setPixels(pixels, 0, 500, 0, 0, bitMatrixWidth, bitMatrixHeight);
        return bitmap;
    }

    public String saveImage(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File qrDirectory = new File(Environment.getExternalStorageDirectory()+IMAGE_DIRECTORY);

        if (!qrDirectory.exists()) {
            qrDirectory.mkdir();
        }

        try {
            File f = new File(qrDirectory, Calendar.getInstance().getTimeInMillis() + ".jpg");
            f.createNewFile();
            FileOutputStream outputStream = new FileOutputStream(f);
            outputStream.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(this, new String[]{f.getPath()},new String[]{"image/jpeg"},null);
            outputStream.close();

            return f.getAbsolutePath();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
