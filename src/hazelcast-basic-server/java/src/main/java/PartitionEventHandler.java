import com.hazelcast.map.MapPartitionLostEvent;
import com.hazelcast.map.listener.MapPartitionLostListener;

public class PartitionEventHandler implements MapPartitionLostListener {

    public void partitionLost (MapPartitionLostEvent event) {
        System.out.println ("- Partition lost.");
        System.out.println ("- " + event);
    }
}
