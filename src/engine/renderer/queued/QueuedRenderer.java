package engine.renderer.queued;

import java.util.ArrayDeque;
import java.util.Queue;

public class QueuedRenderer {
    private static Queue<QueuedRenderCall> renderCalls = new ArrayDeque<>();
    private static int times = 0;

    public static void add(QueuedRenderCall call) {
            QueuedRenderer.renderCalls.add(call);
    }

    public static void renderOnce() {
        QueuedRenderCall queuedRenderCall = QueuedRenderer.renderCalls.poll();
        if(queuedRenderCall != null){
            queuedRenderCall.render();
        }
    }

    public static void renderAll() {
        QueuedRenderCall queuedRenderCall;
        while ((queuedRenderCall = QueuedRenderer.renderCalls.poll())!=null){
            queuedRenderCall.render();
        }
    }

    public static boolean hasQueue() {
        return QueuedRenderer.renderCalls.size() != 0;
    }
}
