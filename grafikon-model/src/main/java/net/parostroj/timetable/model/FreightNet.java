package net.parostroj.timetable.model;

import java.util.*;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import net.parostroj.timetable.actions.FreightHelper;
import net.parostroj.timetable.model.events.*;
import net.parostroj.timetable.visitors.TrainDiagramVisitor;
import net.parostroj.timetable.visitors.Visitable;

/**
 * Managed freight data. Id is shared with train diagram.
 *
 * @author jub
 */
public class FreightNet implements Visitable, ObjectWithId, AttributesHolder {

    private final String id;
    private Attributes attributes;
    private final AttributesListener attributesListener;
    private final GTListenerSupport<FreightNetListener, FreightNetEvent> listenerSupport;

    private final Multimap<Train, FNConnection> fromMap = HashMultimap.create();
    private final Multimap<Train, FNConnection> toMap = HashMultimap.create();
    private final Set<FNConnection> connections = new HashSet<FNConnection>();
    private final Multimap<Node, FNConnection> nodeMap = HashMultimap.create();

    public FreightNet(String id) {
        this.id = id;
        this.attributesListener = new AttributesListener() {
            @Override
            public void attributeChanged(Attributes attributes, AttributeChange change) {
                FreightNetEvent event = null;
                if (attributes instanceof FNConnection) {
                    event = new FreightNetEvent(FreightNet.this, GTEventType.FREIGHT_NET_CONNECTION_ATTRIBUTE, change, (FNConnection) attributes);
                } else {
                    event = new FreightNetEvent(FreightNet.this, change);
                }
                listenerSupport.fireEvent(event);
            }
        };
        this.listenerSupport = new GTListenerSupport<FreightNetListener, FreightNetEvent>(
                new GTEventSender<FreightNetListener, FreightNetEvent>() {
                    @Override
                    public void fireEvent(FreightNetListener listener, FreightNetEvent event) {
                        listener.freightNetChanged(event);
                    }
                });
        this.setAttributes(new Attributes());
    }

    public FNConnection addConnection(TimeInterval from, TimeInterval to) {
        if (from == to || from.getOwnerAsNode() != to.getOwnerAsNode()) {
            throw new IllegalArgumentException(String.format("Invalid connection: %s -> %s", from, to));
        }
        FNConnection conn = new FNConnection(from, to, attributesListener);
        this.addConnectionImpl(conn);
        return conn;
    }

    public void removeConnection(FNConnection conn) {
        this.removeConnectionImpl(conn);
    }

    public Collection<FNConnection> getConnections() {
        return Collections.unmodifiableCollection(connections);
    }

    public Collection<FNConnection> getConnections(Node node) {
        return Collections.unmodifiableCollection(nodeMap.get(node));
    }

    public FNConnection getConnection(TimeInterval from, TimeInterval to) {
        for (FNConnection i : connections) {
            if (i.getFrom() == from && i.getTo() == to) {
                return i;
            }
        }
        return null;
    }

    private void addConnectionImpl(FNConnection conn) {
        connections.add(conn);
        nodeMap.put(conn.getFrom().getOwnerAsNode(), conn);
        this.addConn(fromMap, conn, conn.getFrom().getTrain());
        this.addConn(toMap, conn, conn.getTo().getTrain());
        this.fireEvent(new FreightNetEvent(this, GTEventType.FREIGHT_NET_CONNECTION_ADDED, null, conn));
    }

    private void removeConnectionImpl(FNConnection conn) {
        boolean removed = connections.remove(conn);
        if (removed) {
            nodeMap.remove(conn.getFrom().getOwnerAsNode(), conn);
            this.removeConn(fromMap, conn, conn.getFrom().getTrain());
            this.removeConn(toMap, conn, conn.getTo().getTrain());
            this.fireEvent(new FreightNetEvent(this, GTEventType.FREIGHT_NET_CONNECTION_REMOVED, null, conn));
        }
    }

    private void addConn(Multimap<Train, FNConnection> map, FNConnection conn, Train train) {
        map.put(train, conn);
    }

    private void removeConn(Multimap<Train, FNConnection> map, FNConnection conn, Train train) {
        map.remove(train, conn);
    }

    public void checkTrain(Train train) {
        Collection<FNConnection> connections = this.get(train, fromMap);
        List<FNConnection> toBeDeleted = new ArrayList<FNConnection>();
        for (FNConnection conn : connections) {
            TimeInterval fromInterval = conn.getFrom();
            if (!FreightHelper.isManaged(fromInterval.getTrain()) || !fromInterval.isStop()) {
                toBeDeleted.add(conn);
            }
        }
        connections = this.get(train, toMap);
        for (FNConnection conn : connections) {
            TimeInterval toInterval = conn.getTo();
            if (!FreightHelper.isManaged(toInterval.getTrain()) || !toInterval.isStop()) {
                toBeDeleted.add(conn);
            }
        }
        for (FNConnection conn : toBeDeleted) {
            this.removeConnection(conn);
        }
    }

    public void removeTrain(Train train) {
        List<FNConnection> toBeDeleted = new ArrayList<FNConnection>();
        toBeDeleted.addAll(this.get(train, fromMap));
        toBeDeleted.addAll(this.get(train, toMap));
        for (FNConnection conn : toBeDeleted) {
            this.removeConnection(conn);
        }
    }

    public void addListener(FreightNetListener listener) {
        listenerSupport.addListener(listener);
    }

    public void removeListener(FreightNetListener listener) {
        listenerSupport.removeListener(listener);
    }


    private void fireEvent(FreightNetEvent event) {
        this.listenerSupport.fireEvent(event);
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void accept(TrainDiagramVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public <T> T getAttribute(String key, Class<T> clazz) {
        return this.attributes.get(key, clazz);
    }

    @Override
    public void setAttribute(String key, Object value) {
        this.attributes.set(key, value);
    }

    @Override
    public Object removeAttribute(String key) {
        return this.attributes.get(key);
    }

    @Override
    public Attributes getAttributes() {
        return this.attributes;
    }

    @Override
    public void setAttributes(Attributes attributes) {
        if (this.attributes != null) {
            this.attributes.removeListener(attributesListener);
        }
        this.attributes = attributes;
        this.attributes.addListener(attributesListener);
    }

    public Map<Train, List<FreightDst>> getFreightPassedInNode(TimeInterval fromInterval) {
        if (!fromInterval.isNodeOwner()) {
            throw new IllegalArgumentException("Only node intervals allowed.");
        }
        Map<Train, List<FreightDst>> result = new HashMap<Train, List<FreightDst>>();
        List<FNConnection> connections = this.getTrainsFrom(fromInterval);
        for (FNConnection conn : connections) {
            List<FreightDst> nodes = this.getFreightToNodesImpl(conn.getTo(), conn.getFreightDstFilter(FreightDstFilter.createFilter()));
            result.put(conn.getTo().getTrain(), nodes);
        }
        return result;
    }

    public List<FreightDst> getFreightToNodes(TimeInterval fromInterval) {
        if (!fromInterval.isNodeOwner()) {
            throw new IllegalArgumentException("Only node intervals allowed.");
        }
        return this.getFreightToNodesImpl(fromInterval, new FreightDstFilterEmpty());
    }

    private List<FreightDst> getFreightToNodesImpl(TimeInterval fromInterval, FreightDstFilter filter) {
        List<FreightDst> result = new LinkedList<FreightDst>();
        this.getFreightToNodesImpl(fromInterval, Collections.<TimeInterval>emptyList(), result, new HashSet<FNConnection>(), filter);
        return result;
    }

    private void getFreightToNodesImpl(TimeInterval fromInterval, List<TimeInterval> path, List<FreightDst> result, Set<FNConnection> used, FreightDstFilter filter) {
        List<FNConnection> nextConns = getNextTrains(fromInterval);
        for (TimeInterval i : FreightHelper.getNodeIntervalsWithFreightOrConnection(fromInterval.getTrain().getTimeIntervalList(), fromInterval, this)) {
            if (FreightHelper.isFreight(i)) {
                FreightDst newDst = new FreightDst(i.getOwnerAsNode(), i.getTrain(), path);
                if (!filter.accepted(newDst, 0)) {
                    break;
                }
                result.add(newDst);
            }
            for (FNConnection conn : nextConns) {
                if (i == conn.getFrom() && !used.contains(conn)) {
                    used.add(conn);
                    List<TimeInterval> newPath = new ArrayList<TimeInterval>(path.size() + 1);
                    newPath.addAll(path);
                    newPath.add(conn.getFrom());
                    this.getFreightToNodesImpl(conn.getTo(), newPath, result, used, conn.getFreightDstFilter(filter));
                }
            }
        }
        Collection<Node> rtNodes = getRegionTransferNodes(fromInterval);
        for (Node rtNode : rtNodes) {
            FreightDst regionDst = new FreightDst(rtNode, null);
            if (filter.accepted(regionDst, 1)) {
                result.add(regionDst);
            }
        }
    }

    private Collection<Node> getRegionTransferNodes(TimeInterval fromInterval) {
        Train train = fromInterval.getTrain();
        if (FreightHelper.isFreightTo(train.getLastInterval()) &&
                !FreightHelper.isStartRegion(train.getFirstInterval()) &&
                FreightHelper.isStartRegion(train.getLastInterval()) &&
                !FreightHelper.isNoTransitiveRegionStart(train.getLastInterval())) {
            Set<Node> result = new HashSet<Node>();
            Node tNode = train.getEndNode();
            for (NodeTrack track : tNode.getTracks()) {
                for (TimeInterval interval : track.getTimeIntervalList()) {
                    Train tTrain = interval.getTrain();
                    if (tNode == tTrain.getStartNode() && FreightHelper.isRegionTransferTrain(tTrain)) {
                        result.add(tTrain.getLastInterval().getOwnerAsNode());
                    }
                }
            }
            return result;
        } else {
            return Collections.emptySet();
        }
    }

    public List<FNConnection> getNextTrains(TimeInterval fromInterval) {
        List<FNConnection> result = new LinkedList<FNConnection>();
        int index = fromInterval.getTrain().getIndexOfInterval(fromInterval);
        Collection<FNConnection> connections = this.get(fromInterval.getTrain(), fromMap);
        for (FNConnection conn : connections) {
            int indexConn = conn.getFrom().getTrain().getIndexOfInterval(conn.getFrom());
            if (indexConn > index) {
                result.add(conn);
            }
        }
        return result;
    }

    public List<FNConnection> getTrainsFrom(TimeInterval fromInterval) {
        List<FNConnection> result = new LinkedList<FNConnection>();
        Collection<FNConnection> connections = this.get(fromInterval.getTrain(), fromMap);
        for (FNConnection conn : connections) {
            if (fromInterval == conn.getFrom()) {
                result.add(conn);
            }
        }
        return result;
    }

    public List<FNConnection> getTrainsTo(TimeInterval toInterval) {
        List<FNConnection> result = new LinkedList<FNConnection>();
        Collection<FNConnection> connections = this.get(toInterval.getTrain(), toMap);
        for (FNConnection conn : connections) {
            if (toInterval == conn.getTo()) {
                result.add(conn);
            }
        }
        return result;
    }

    private Collection<FNConnection> get(Train train, Multimap<Train, FNConnection> map) {
        return map.get(train);
    }

    @Override
    public String toString() {
        return "FreightNet";
    }
}
