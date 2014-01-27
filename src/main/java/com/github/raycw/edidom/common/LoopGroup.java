package com.github.raycw.edidom.common;

import java.util.ArrayList;
import java.util.List;

public class LoopGroup {
    
    private List<Segment> segments = new ArrayList<Segment>();
    private Transaction txn;
    
    public LoopGroup(Transaction txn) {
        this.txn = txn;
    }
    
    public List<Segment> getSegments() {
        return segments;
    }
    
    public LoopGroup addSegment(Segment segment) {
        segments.add(segment);
        return this;
    }
    
    public Transaction getTransaction() {
        return txn;
    }
    
    public List<LoopGroup> getLoopGroups(String startTag, String endTag) {
        List<LoopGroup> loopGroups = new ArrayList<LoopGroup>();
        List<Segment> segments = this.getSegments(startTag);
        if (segments.size() == 0) {
            return loopGroups;
        }
        for (int i = 0; i < segments.size() -1; i++) {
            Segment segment = segments.get(i);
            LoopGroup loop = new LoopGroup(txn);
            loop.addSegment(segment);
            segment = segment.nextSegment();
            while (!segment.getSegmentTag().equals(startTag)) {
                loop.addSegment(segment);
                segment = segment.nextSegment();
            }
            loopGroups.add(loop);
        }
        Segment segment = segments.get(segments.size()-1);
        LoopGroup loop = new LoopGroup(txn);
        loop.addSegment(segment);
        segment = segment.nextSegment();
        while (segment != null && !segment.getSegmentTag().equals(endTag)) {
            loop.addSegment(segment);
            segment = segment.nextSegment();
        }
        loopGroups.add(loop);
        return loopGroups;
    }
    
    public List<Segment> getSegments(String tag) {
        List<Segment> segments = new ArrayList<Segment>();
        for (Segment segment : this.segments) {
            if (segment.getSegmentTag().equals(tag)) {
                segments.add(segment);
            }
        }
        return segments;
    }

}
