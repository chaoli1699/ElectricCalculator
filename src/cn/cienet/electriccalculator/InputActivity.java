package cn.cienet.electriccalculator;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import cn.cienet.electriccalculator.presenter.InputPresenter;
import cn.cienet.electriccalculator.view.InputView;

public class InputActivity extends BaseActivity<InputPresenter> implements InputView {

	private EditText content;
	private int requestCode;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestCode=getIntent().getIntExtra("requestCode", -1);
		String title="InputActivity";
		if (requestCode==INSERT_USER) {
			title=getResources().getString(R.string.insert_user);
		}else if (requestCode==INPUT_TOTAL) {
			title=getResources().getString(R.string.input_total_money);
		}else if (requestCode==SET_USER_SIZE) {
			title=getResources().getString(R.string.set_user_max);
		}
		initActionBar(title, false, true);
		setContentView(R.layout.activity_input);
		
		initView();
	}
	
    private void initView() {
		// TODO Auto-generated method stub
        content=(EditText) findViewById(R.id.input_content);
        
        if (requestCode==INSERT_USER) {
			content.setInputType(EditorInfo.TYPE_CLASS_TEXT);
		}else if (requestCode==INPUT_TOTAL || requestCode== SET_USER_SIZE) {
			content.setInputType(EditorInfo.TYPE_CLASS_NUMBER);
		}
	}
	
	@Override
	protected InputPresenter createPresenter() {
		// TODO Auto-generated method stub
		return new InputPresenter(InputActivity.this, this);
	}
	
	@Override
	public void inputComplate(String content) {
		// TODO Auto-generated method stub
		if (requestCode==INSERT_USER) {
			this.setResult(RESULT_OK);
		}else if (requestCode==INPUT_TOTAL) {
			Intent intent=new Intent();
			intent.putExtra("total", content);
			this.setResult(RESULT_OK, intent);
		}else if (requestCode==SET_USER_SIZE) {
			this.setResult(RESULT_CANCELED);
		}
		this.finish();
	}
	
	@Override
	public void inputFail(String msg) {
		// TODO Auto-generated method stub
	    showToast(msg);	
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		getMenuInflater().inflate(R.menu.input, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case android.R.id.home:
			InputActivity.this.setResult(RESULT_CANCELED);
			InputActivity.this.finish();
			break;
		case R.id.input_ok:
			String contentStr=content.getText().toString().trim();
			if (contentStr.length()>0) {
				if (requestCode==INSERT_USER) {
					mPresenter.insertUser(contentStr);
				}else if (requestCode==INPUT_TOTAL) {
					mPresenter.inpuTotal(Float.valueOf(contentStr));
				}else if (requestCode==SET_USER_SIZE) {
					mPresenter.setUserSizeMax(Integer.valueOf(contentStr));
				}
				
			}
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
}
