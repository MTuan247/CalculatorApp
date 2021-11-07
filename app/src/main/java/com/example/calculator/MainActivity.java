package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.concurrent.Callable;

public class MainActivity extends AppCompatActivity {

    Button[] numberBtn;
    Button btnDiv, btnClear, btnReset;
    ImageButton btnDel, btnResult, btnMul, btnMinus, btnAdd;
    TextView mainContent, calcView;

    int firstNumber = 0;
    String lastOperation = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        numberBtn = new Button[10];
        numberBtn[0] = findViewById(R.id.btn_zero);
        numberBtn[1] = findViewById(R.id.btn_one);
        numberBtn[2] = findViewById(R.id.btn_two);
        numberBtn[3] = findViewById(R.id.btn_three);
        numberBtn[4] = findViewById(R.id.btn_four);
        numberBtn[5] = findViewById(R.id.btn_five);
        numberBtn[6] = findViewById(R.id.btn_six);
        numberBtn[7] = findViewById(R.id.btn_seven);
        numberBtn[8] = findViewById(R.id.btn_eight);
        numberBtn[9] = findViewById(R.id.btn_nine);
        btnAdd = findViewById(R.id.btn_add);
        btnMinus = findViewById(R.id.btn_minus);
        btnMul = findViewById(R.id.btn_mul);
        btnDiv = findViewById(R.id.btn_divide);
        btnResult = findViewById(R.id.btn_res);
        btnDel = findViewById(R.id.btn_del);
        btnClear = findViewById(R.id.btn_ce);
        btnReset = findViewById(R.id.btn_c);
        mainContent = findViewById(R.id.calc_content);
        calcView = findViewById(R.id.calc_result);

        for (int i = 0; i < 10; i++) {
            setAppendViewOnClickEvent(numberBtn[i], String.valueOf(i));
        }

        setOperationClickEvent(btnAdd, "+");
        setOperationClickEvent(btnDiv, "/");
        setOperationClickEvent(btnMul, "x");
        setOperationClickEvent(btnMinus, "-");

        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteEvent();
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetEvent();
            }
        });

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearEvent();
            }
        });

        btnResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleCalculateEvent();
            }
        });

    }

    /**
     * Thêm sự kiện thêm kí tự trên text view chính
     * @param btn
     * @param i
     */
    protected void setAppendViewOnClickEvent(View btn, String i) {
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appendToView(i);
            }
        };
        btn.setOnClickListener(listener);
    }

    /**
     * @param btn
     * @param operation
     */
    protected void setOperationClickEvent(View btn, String operation) {
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleOperationClickEvent(operation);
            }
        };
        btn.setOnClickListener(listener);
    }

    /**
     * Thêm kí tự trên text view chính
     * @param append
     */
    protected void appendToView(String append) {
        String content = mainContent.getText().toString();

        content = content + append;

        mainContent.setText(content);

    }

    /**
     *
     * @param operation
     */
    protected void handleOperationClickEvent(String operation) {

        String content = mainContent.getText().toString();

        if (lastOperation != null && lastOperation != "" ) {
            int secondNumber = content != "" ? Integer.parseInt(content) : 0;
            int result;
            switch (lastOperation) {
                case "/":
                    result = firstNumber / secondNumber;
                    break;
                case "x":
                    result = firstNumber * secondNumber;
                    break;
                case "+":
                    result = firstNumber + secondNumber;
                    break;
                case "-":
                    result = firstNumber - secondNumber;
                    break;
                default:
                    result = 0;
                    break;
            }

            mainContent.setText(String.valueOf(result));

            calcView.setText(String.valueOf(result) + " " + operation);

            saveNumber();
            clearEvent();

        } else {
            calcView.setText(content + " " + operation);
            saveNumber();
            clearEvent();
        }


        lastOperation = operation;

    }

    protected void handleCalculateEvent() {
        if (lastOperation != null && lastOperation != "") {
            String content = mainContent.getText().toString();
            int secondNumber = content != "" ? Integer.parseInt(content) : 0;
            int result;
            switch (lastOperation) {
                case "/":
                    if (secondNumber == 0) {
                        result = 0;
                        break;
                    };
                    result = firstNumber / secondNumber;
                    break;
                case "x":
                    result = firstNumber * secondNumber;
                    break;
                case "+":
                    result = firstNumber + secondNumber;
                    break;
                case "-":
                    result = firstNumber - secondNumber;
                    break;
                default:
                    result = 0;
                    break;
            }
            mainContent.setText(String.valueOf(result));
            calcView.setText(String.valueOf(firstNumber) + lastOperation + secondNumber + "=");
            firstNumber = result;
            lastOperation = null;
        }
    }

    /**
     * Lưu giá trị hiện tại
     */
    protected void saveNumber() {
        String content = mainContent.getText().toString();
        firstNumber = Integer.parseInt(content);
    }

    /**
     * Lưu giá trị truyền vào
     * @param number
     */
    protected void saveNumber(int number) {
        firstNumber = number;
    }

    /**
     * Xóa 1 kí tự
     */
    protected void deleteEvent() {
        String str = mainContent.getText().toString();

        if (str != null && str.length() > 0) {
            str = str.substring(0, str.length() - 1);
        }

        mainContent.setText(str);
    }

    /**
     * Xóa toàn bộ text view chính
     */
    protected void clearEvent() {
        mainContent.setText("");
    }

    /**
     * Xóa toàn bộ text view
     */
    protected void resetEvent() {
        calcView.setText("");
        mainContent.setText("");
        firstNumber = 0;
        lastOperation = "";
    }
}