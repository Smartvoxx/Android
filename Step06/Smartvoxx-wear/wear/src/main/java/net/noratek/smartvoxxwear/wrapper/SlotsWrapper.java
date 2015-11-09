package net.noratek.smartvoxxwear.wrapper;

import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.DataMapItem;

import net.noratek.smartvoxxwear.model.BreakSession;
import net.noratek.smartvoxxwear.model.Slot;
import net.noratek.smartvoxxwear.model.Talk;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eloudsa on 03/09/15.
 */
public class SlotsWrapper {

    public List<Slot> getSlotsList(DataEvent dataEvent) {

        List<Slot> slotsList = new ArrayList<>();

        if (dataEvent == null) {
            return slotsList;
        }

        DataMapItem dataMapItem = DataMapItem.fromDataItem(dataEvent.getDataItem());
        if (dataMapItem == null) {
            return slotsList;
        }

        return getSlotsList(dataMapItem.getDataMap());
    }



    public List<Slot> getSlotsList(DataMap dataMap1) {

        List<Slot> slotsList = new ArrayList<>();

        if (dataMap1 == null) {
            return slotsList;
        }

        List<DataMap> slotsDataMap = dataMap1.getDataMapArrayList("/list");
        if (slotsDataMap == null) {
            return slotsList;
        }

        for (DataMap slotDataMap : slotsDataMap) {
            // retrieve the speaker's information

            Slot slot = new Slot();


            slot.setSlotId(slotDataMap.getString("slotId"));
            slot.setRoomName(slotDataMap.getString("roomName"));
            slot.setFromTimeMillis(slotDataMap.getLong("fromTimeMillis"));
            slot.setToTimeMillis(slotDataMap.getLong("toTimeMillis"));


            DataMap breakDataMap = slotDataMap.getDataMap("break");
            if (breakDataMap != null) {
                BreakSession breakSlot = new BreakSession();

                breakSlot.setId(breakDataMap.getString("id"));
                breakSlot.setNameEN(breakDataMap.getString("nameEN"));
                breakSlot.setNameFR(breakDataMap.getString("nameFR"));

                slot.setBreak(breakSlot);
            }

            DataMap talkDataMap = slotDataMap.getDataMap("talk");
            if (talkDataMap != null) {
                Talk talkSlot = new Talk();

                talkSlot.setId(talkDataMap.getString("id"));
                talkSlot.setEventId(talkDataMap.getLong("eventId"));
                talkSlot.setLang(talkDataMap.getString("lang"));
                talkSlot.setSummary(talkDataMap.getString("summary"));
                talkSlot.setTalkType(talkDataMap.getString("talkType"));
                talkSlot.setTitle(talkDataMap.getString("title"));
                talkSlot.setTrack(talkDataMap.getString("track"));
                talkSlot.setTrackId(talkDataMap.getString("trackId"));

                slot.setTalk(talkSlot);
            }

            // skip unknown talks
            if ((slot.getBreak() != null) || (slot.getTalk() != null)) {
                slotsList.add(slot);
            }


        }

        return slotsList;

    }

}
