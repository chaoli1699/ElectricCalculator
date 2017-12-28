package cn.cienet.electriccalculator.widget;

import java.util.Arrays;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;
import cn.cienet.electriccalculator.R;
import cn.cienet.electriccalculator.utils.FormatUtils;

public class LineChart extends View{

	private float xLength;
    private float yLength;
    private float startPointX;
    private float startPointY;
    private float xScale;
    private float yScale;
    private float coordTextSize;

    private String[] xLabel;
    private String[] yLabel;
    private String[] data;
    private String title;

    private String[] mDataLineColors;
    private float[][] mDataCoords = new float[7][2];

    private Paint mScaleLinePaint;
    private Paint mDataLinePaint;
    private Paint mScaleValuePaint;
    private Paint mBackColorPaint;

    Rect bounds = new Rect();

    private boolean isClick;                // �Ƿ��������ݵ�
    private int clickIndex = -1;            // ����������ݵ������ֵ

    private PopupWindow mPopWin;

    public LineChart(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        this.setBackgroundColor(Color.WHITE);
        // x��̶�ֵ
        if (xLabel == null) {
            xLabel = new String[]{"06-30", "07-31", "08-31", "09-30", "10-31", "11-30", "12-31"};
        }
        // ���ݵ�
        if (data == null) {
            data = new String[]{"0.00", "0.00", "0.00", "0.00", "0.00", "0.00", "0.00"};
        }
        // ����
        if (title == null) {
            title = "�������˵�(RMB)";
        }

        // �������õ�����ֵ����Y����̶�ֵ
        yLabel = createYLabel();

        mDataLineColors = new String[]{"#fbbc14", "#fbaa0c", "#fbaa0c", "#fb8505", "#ff6b02", "#ff5400", "#ff5400"};
        // �½�����
        mDataLinePaint = new Paint();       // ����(�������)����
        mScaleLinePaint = new Paint();      // ����(�̶�����)ֵ����
        mScaleValuePaint = new Paint();      // ͼ��(�̶�ֵ)����
        mBackColorPaint = new Paint();       // ����(ɫ��)����
        // ���ʿ����
        mDataLinePaint.setAntiAlias(true);
        mScaleLinePaint.setAntiAlias(true);
        mScaleValuePaint.setAntiAlias(true);
        mBackColorPaint.setAntiAlias(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // ����Ͳ�ȥ�жϼ��ֲ���ģʽ�ˣ�ֱ�����ô�С
        // ��Ҫ������ֱַ�����Ļ�Ŀ����и��ݲ���ģʽ���������մ�С
        initParams();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawBackColor(canvas);              // ���Ʊ���ɫ��
        drawYAxisAndXScaleValue(canvas);    // ����y���x�̶�ֵ
        drawXAxisAndYScaleValue(canvas);    // ����x���y�̶�ֵ
        drawDataLines(canvas);              // ������������
        drawDataPoints(canvas);             // �������ݵ�
        drawTitle(canvas);                  // ���Ʊ���
    }

    /**
     * ���Ʊ���ɫ��
     *
     * @param canvas
     */
    private void drawBackColor(Canvas canvas) {
        for (int i = 0; i < 7; i++) {
            if (i == 2 || i == 4 || i == 6) {
                canvas.drawRect(startPointX + (i - 1) * xScale,
                        startPointY,
                        startPointX + i * xScale,
                        yLength + startPointY,
                        mBackColorPaint);
            }
        }
    }

    private void drawYAxisAndXScaleValue(Canvas canvas) {
        for (int i = 0; i < 7; i++) {
            canvas.drawLine(startPointX + i * xScale,
                    startPointY,
                    startPointX + i * xScale,
                    startPointY + yLength,
                    mScaleLinePaint);
            mScaleValuePaint.getTextBounds(xLabel[i], 0, xLabel[i].length(), bounds);
            if (i == 0) {
                canvas.drawText(xLabel[i],
                        startPointX,
                        startPointY + yLength + bounds.height() + yScale / 15,
                        mScaleValuePaint);
            } else {
                canvas.drawText(xLabel[i],
                        startPointX + i * xScale - bounds.width() / 2,
                        startPointY + yLength + bounds.height() + yScale / 15,
                        mScaleValuePaint);
            }
        }
    }

    /**
     * ����x���y�̶�ֵ
     *
     * @param canvas
     */
    private void drawXAxisAndYScaleValue(Canvas canvas) {
        for (int i = 0; i < 6; i++) {
            if (i < 5) {
                mScaleValuePaint.getTextBounds(yLabel[4 - i], 0, yLabel[4 - i].length(), bounds);
                canvas.drawText(yLabel[4 - i],
                        startPointX + xScale / 15,
                        startPointY + yScale * (i + 0.5f) + bounds.height() / 2,
                        mScaleValuePaint);
                canvas.drawLine(startPointX + bounds.width() + 2 * xScale / 15,
                        startPointY + (i + 0.5f) * yScale,
                        startPointX + xLength,
                        startPointY + (i + 0.5f) * yScale,
                        mScaleLinePaint);
            } else {
                canvas.drawLine(startPointX,
                        startPointY + (i + 0.5f) * yScale,
                        startPointX + xLength,
                        startPointY + (i + 0.5f) * yScale,
                        mScaleLinePaint);
            }
        }
    }

    /**
     * ������������
     *
     * @param canvas
     */
    private void drawDataLines(Canvas canvas) {
        getDataRoords();
        for (int i = 0; i < 6; i++) {
            mDataLinePaint.setColor(Color.parseColor(mDataLineColors[i]));
            canvas.drawLine(mDataCoords[i][0], mDataCoords[i][1], mDataCoords[i + 1][0], mDataCoords[i + 1][1], mDataLinePaint);
        }
    }

    /**
     * �������ݵ�
     *
     * @param canvas
     */
    private void drawDataPoints(Canvas canvas) {
        // ����󣬻������ݵ�
        if (isClick && clickIndex > -1) {
            mDataLinePaint.setColor(Color.parseColor(mDataLineColors[clickIndex]));
            canvas.drawCircle(mDataCoords[clickIndex][0], mDataCoords[clickIndex][1], xScale / 10, mDataLinePaint);
            mDataLinePaint.setColor(Color.WHITE);
            canvas.drawCircle(mDataCoords[clickIndex][0], mDataCoords[clickIndex][1], xScale / 20, mDataLinePaint);
            mDataLinePaint.setColor(Color.parseColor(mDataLineColors[clickIndex]));
        }
    }

    /**
     * ���Ʊ���
     *
     * @param canvas
     */
    private void drawTitle(Canvas canvas) {
        // ���Ʊ����ı�������
        mDataLinePaint.getTextBounds(title, 0, title.length(), bounds);
        canvas.drawText(title, (getWidth() - bounds.width()) / 2, startPointY + yLength + yScale, mDataLinePaint);
        canvas.drawLine((getWidth() - bounds.width()) / 2 - xScale / 15,
                startPointY + yLength + yScale - bounds.height() / 2 + coordTextSize / 4,
                (getWidth() - bounds.width()) / 2 - xScale / 2,
                startPointY + yLength + yScale - bounds.height() / 2 + coordTextSize / 4,
                mDataLinePaint);
    }

    @SuppressLint("ClickableViewAccessibility")
	@Override
    public boolean onTouchEvent(MotionEvent event) {
        float touchX = event.getX();
        float touchY = event.getY();
        for (int i = 0; i < 7; i++) {
            float dataX = mDataCoords[i][0];
            float dataY = mDataCoords[i][1];
            // ���ƴ���/����ķ�Χ������Ч��Χ�ڲŴ���
            if (Math.abs(touchX - dataX) < xScale / 2 && Math.abs(touchY - dataY) < yScale / 2) {
                isClick = true;
                clickIndex = i;
                invalidate();     // �ػ�չʾ���ݵ�СԲȦ
                showDetails(i);   // ͨ��PopupWindowչʾ��ϸ������Ϣ
                return true;
            } else {
                hideDetails();
            }
            clickIndex = -1;
            invalidate();
        }
        return super.onTouchEvent(event);
    }

    /**
     * ������ݵ��չʾ��ϸ������ֵ
     */
    private void showDetails(int index) {
        if (mPopWin != null) mPopWin.dismiss();
        TextView tv = new TextView(getContext());
        tv.setTextColor(Color.WHITE);
        tv.setBackgroundResource(R.drawable.shape_pop_bg);
        GradientDrawable myGrad = (GradientDrawable) tv.getBackground();
        myGrad.setColor(Color.parseColor(mDataLineColors[index]));
        tv.setPadding(20, 0, 20, 0);
        tv.setGravity(Gravity.CENTER);
        tv.setText(data[index] + "RMB");
        mPopWin = new PopupWindow(tv, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopWin.setBackgroundDrawable(new ColorDrawable(0));
        mPopWin.setFocusable(false);
        // ����������λ�ü��㵯����չʾλ��
        int xoff = (int) (mDataCoords[index][0] - 0.5 * xScale);
        int yoff = -(int) (getHeight() - mDataCoords[index][1] + 0.75f * yScale);
        mPopWin.showAsDropDown(this, xoff, yoff);
        mPopWin.update();
    }

    private void hideDetails() {
        if (mPopWin != null) mPopWin.dismiss();
    }

    private void initParams() {
        int width = getMeasuredWidth() - getPaddingLeft() - getPaddingRight();
        int height = getMeasuredHeight() - getPaddingTop() - getPaddingBottom();

        yScale = height / 7.5f;         // y��̶�
        xScale = width / 7.5f;          // x��̶�
        startPointX = xScale / 2;       // ��ʼ��ͼ��x����
        startPointY = yScale / 2;       // ��ʼUIͼ��y����
        xLength = 6.5f * xScale;        // x�᳤��
        yLength = 5.5f * yScale;        // y�᳤��

        float chartLineStrokeWidth = xScale / 50;     // ͼ���������߿�
        coordTextSize = xScale / 5;             // ����̶����ֵĴ�С
        float dataLineStrodeWidth = xScale / 15;      // �����������߿�

        // ���û����������
        mBackColorPaint.setColor(0x11DEDE68);
        mScaleLinePaint.setStrokeWidth(chartLineStrokeWidth);
        mScaleLinePaint.setColor(0xFFDEDCD8);
        mScaleValuePaint.setColor(0xFF999999);
        mScaleValuePaint.setTextSize(coordTextSize);
        mDataLinePaint.setStrokeWidth(dataLineStrodeWidth);
        mDataLinePaint.setStrokeCap(Paint.Cap.ROUND);
        mDataLinePaint.setTextSize(1.5f * coordTextSize);
    }

    /**
     * ��������ֵdata���ɺ��ʵ�Y����̶�ֵ
     *
     * @return y������̶�ֵ����
     */
    private String[] createYLabel() {
        float[] dataFloats = new float[7];
        for (int i = 0; i < data.length; i++) {
            dataFloats[i] = Float.parseFloat(data[i]);
        }
        // ������ֵ��С��������
        Arrays.sort(dataFloats);
        // �м�ֵ
        float middle =FormatUtils.format2Bit((dataFloats[0] + dataFloats[6]) / 2f);
        // Y�̶�ֵ
        float scale = FormatUtils.format2Bit((dataFloats[6] - dataFloats[0]) / 4 + 0.01f);
        String[] yText = new String[5];
        yText[0] = (middle - 2 * scale) + "";
        yText[1] = (middle - scale) + "";
        yText[2] = middle + "";
        yText[3] = (middle + scale) + "";
        yText[4] = (middle + 2 * scale) + "";
        for (int i = 0; i < yText.length; i++) {
            yText[i] = FormatUtils.format2Bit(yText[i]);
        }
        return yText;
    }

    /**
     * ��ȡ����ֵ�������
     *
     * @return ���ݵ������
     */
    private void getDataRoords() {
        float originalPointX = startPointX;
        float originalPointY = startPointY + yLength - yScale;
        for (int i = 0; i < data.length; i++) {
            mDataCoords[i][0] = originalPointX + i * xScale;
            float dataY = Float.parseFloat(data[i]);
            float oriY = Float.parseFloat(yLabel[0]);
            mDataCoords[i][1] = originalPointY - (yScale * (dataY - oriY) / (Float.parseFloat(yLabel[1]) - Float.parseFloat(yLabel[0])));
        }
    }

    /**
     * ����x��̶�ֵ
     *
     * @param xLabel x�̶�ֵ
     */
    public void setxLabel(String[] xLabel) {
        this.xLabel = xLabel;
    }

    /**
     * ��������
     *
     * @param data ����ֵ
     */
    public void setData(String[] data) {
        this.data = data;
    }

    /**
     * ���ñ���
     *
     * @param title ����
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * ��������x��̶ȡ����ݡ���������ˢ���ػ�
     */
    public void fresh() {
        init();
        requestLayout();
        postInvalidate();
    }
}
