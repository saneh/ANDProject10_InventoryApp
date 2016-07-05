package in.lemonco.todos;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

/*
 * TodoDetailActivity allows to enter a new todo item
 * or to change an existing
 */
public class TodoDetailActivity extends Activity {
    private EditText mCategory;
    private EditText mTitleText;
    private EditText mBodyText;

    private Uri todoUri;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.todo_edit);

        mCategory = (EditText) findViewById(R.id.todo_name);
        mTitleText = (EditText) findViewById(R.id.todo_edit_summary);
        mBodyText = (EditText) findViewById(R.id.todo_edit_description);
        Button confirmButton = (Button) findViewById(R.id.todo_edit_button);

        Bundle extras = getIntent().getExtras();

        // check from the saved Instance
        todoUri = (bundle == null) ? null : (Uri) bundle
                .getParcelable(MyTodoContentProvider.CONTENT_ITEM_TYPE);

        // Or passed from the other activity
        if (extras != null) {
            todoUri = extras
                    .getParcelable(MyTodoContentProvider.CONTENT_ITEM_TYPE);

            fillData(todoUri);
        }

        confirmButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (TextUtils.isEmpty(mTitleText.getText().toString())) {
                    makeToast();
                } else {
                    setResult(RESULT_OK);
                    finish();
                }
            }

        });
    }

    private void fillData(Uri uri) {
        String[] projection = { TodoTable.COLUMN_NAME,
                TodoTable.COLUMN_SUPPLIER, TodoTable.COLUMN_IMAGE };
        Cursor cursor = getContentResolver().query(uri, projection, null, null,
                null);
        if (cursor != null) {
            cursor.moveToFirst();
            mCategory.setText(cursor.getString(cursor
                    .getColumnIndexOrThrow(TodoTable.COLUMN_NAME)));
            mTitleText.setText(cursor.getString(cursor
                    .getColumnIndexOrThrow(TodoTable.COLUMN_SUPPLIER)));
            mBodyText.setText(cursor.getString(cursor
                    .getColumnIndexOrThrow(TodoTable.COLUMN_IMAGE)));

            // always close the cursor
            cursor.close();
        }
    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        saveState();
        outState.putParcelable(MyTodoContentProvider.CONTENT_ITEM_TYPE, todoUri);
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveState();
    }

    private void saveState() {
        String category = mCategory.getText().toString();
        String summary = mTitleText.getText().toString();
        String description = mBodyText.getText().toString();

        // only save if either summary or description
        // is available

        if (description.length() == 0 && summary.length() == 0) {
            return;
        }

        ContentValues values = new ContentValues();
        values.put(TodoTable.COLUMN_NAME, category);
        values.put(TodoTable.COLUMN_SUPPLIER, summary);
        values.put(TodoTable.COLUMN_IMAGE, description);

        if (todoUri == null) {
            // New todo
            todoUri = getContentResolver().insert(
                    MyTodoContentProvider.CONTENT_URI, values);
        } else {
            // Update todo
            getContentResolver().update(todoUri, values, null, null);
        }
    }

    private void makeToast() {
        Toast.makeText(TodoDetailActivity.this, "Please maintain a summary",
                Toast.LENGTH_LONG).show();
    }

}