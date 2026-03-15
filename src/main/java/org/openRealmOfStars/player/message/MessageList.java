package org.openRealmOfStars.player.message;
/*
 * Open Realm of Stars game project
 * Copyright (C) 2016 Tuomo Untinen
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see http://www.gnu.org/licenses/
 */

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.openRealmOfStars.gui.icons.Icons;

/**
 * Message list for handling messages per player
 */
public class MessageList {

  /**
   * No messages message if there are no messages
   */
  private static final Message NO_MESSAGE = new Message(MessageType.INFORMATION,
      "No messages", Icons.getIconByName(Icons.ICON_EMPTY));

  /**
   * No messages message if there are no messages
   */
  private static final Message FILTERED_MESSAGE = new Message(
      MessageType.INFORMATION, "This message has been filtered out and will"
          + " not be shown unless filter is removed, or show all messages is"
          + " selected.", Icons.getIconByName(Icons.ICON_EMPTY));

  /**
   * List of messages
   */
  private ArrayList<Message> list;

  /**
   * Filtered message types.
   */
  private ArrayList<MessageType> filterList;
  /**
   * List of message which are done in previous turn but
   * will be shown during next turn. These are not needed
   * to be saved.
   */
  private ArrayList<Message> upComingList;

  /**
   * Current message index;
   */
  private int index;

  /**
   * Flag if filter is applied.
   */
  private boolean applyFilter;
  /**
   * Constructor for message list
   */
  public MessageList() {
    list = new ArrayList<>();
    filterList = new ArrayList<>();
    upComingList = new ArrayList<>();
    index = 0;
    setApplyFilter(true);
  }

  /**
   * Read all the message from DataInputStream to list
   * @param dis DataInputStream
   * @throws IOException if there is any problem with DataInputStream
   */
  public MessageList(final DataInputStream dis) throws IOException {
    list = new ArrayList<>();
    filterList = new ArrayList<>();
    upComingList = new ArrayList<>();
    index = 0;
    setApplyFilter(true);
    int count = dis.readInt();
    for (int i = 0; i < count; i++) {
      list.add(new Message(dis));
    }
    count = dis.readInt();
    for (int i = 0; i < count; i++) {
      MessageType type = MessageType.getTypeByIndex(dis.readInt());
      filterList.add(type);
    }
  }

  /**
   * Write all messages and filters into DataOutputStream
   * @param dos DataOutputStream
   * @throws IOException if there is any problem with DataOutputStream
   */
  public void saveMessageList(final DataOutputStream dos) throws IOException {
    dos.writeInt(list.size());
    for (int i = 0; i < list.size(); i++) {
      list.get(i).saveMessage(dos);
    }
    dos.writeInt(filterList.size());
    for (int i = 0; i < filterList.size(); i++) {
      dos.writeInt(filterList.get(i).getIndex());
    }
  }

  /**
   * Clear all messages from the list
   */
  public void clearMessages() {
    list = upComingList;
    upComingList = new ArrayList<>();
    index = 0;
  }

  /**
   * Add filter for certain message type. Does not add duplicates.
   * @param filter MessageType
   */
  public void addFilterType(final MessageType filter) {
    boolean found = false;
    for (MessageType type : filterList) {
      if (type == filter) {
        found = true;
      }
    }
    if (!found) {
      filterList.add(filter);
    }
  }
  /**
   * Add filter for certain message type. Does not add duplicates.
   * @param filter MessageType
   */
  public void removeFilterType(final MessageType filter) {
    filterList.remove(filter);
  }

  /**
   * Is certain message type filtered or not.
   * This also check if filter is actually applied.
   * @param filter MessageType to filter?
   * @return True if filtered, otherwise false.
   */
  public boolean isFiltered(final MessageType filter) {
    if (!applyFilter) {
      return false;
    }
    for (MessageType type : filterList) {
      if (type == filter) {
        return true;
      }
    }
    return false;
  }

  /**
   * Is certain message type filtered or not.
   * This will NOT check if filter is actually applied.
   * @param filter MessageType to filter?
   * @return True if filtered, otherwise false.
   */
  public boolean isFilteredIgnoreApply(final MessageType filter) {
    for (MessageType type : filterList) {
      if (type == filter) {
        return true;
      }
    }
    return false;
  }

  /**
   * Is current message filtered?
   * This will just check the type not if filter is applied.
   * @return True if filtered
   */
  public boolean isFiltered() {
    if (list.size() == 0) {
      return false;
    }
    Message msg = list.get(index);
    for (MessageType type : filterList) {
      if (type == msg.getType()) {
        return true;
      }
    }
    return false;
  }
  /**
   * Add new message to list
   * @param msg Message to add to the list
   */
  public void addNewMessage(final Message msg) {
    list.add(msg);
  }

  /**
   * Add new message to list as a first one.
   * @param msg Message to add to the list
   */
  public void addFirstMessage(final Message msg) {
    list.add(0, msg);
  }

  /**
   * Add upcoming new message to list
   * @param msg Message to add to the list
   */
  public void addUpcomingMessage(final Message msg) {
    upComingList.add(msg);
  }

  /**
   * Get full list of messages
   * @return ArrayList of messages
   */
  public ArrayList<Message> getFullList() {
    return list;
  }

  /**
   * Get next message and move current message position to forward
   * if there are still messages left
   * @return Message, never null
   */
  public Message getNextMessage() {
    if (list.size() == 0) {
      return NO_MESSAGE;
    }
    if (index < list.size() - 1) {
      index++;
    }
    return getFilteredMsg();
  }

  /**
   * Get previous message and move current message position to backward
   * if there are still messages left
   * @return Message, never null
   */
  public Message getPrevMessage() {
    if (list.size() == 0) {
      return NO_MESSAGE;
    }
    if (index > 0) {
      index--;
    }
    return getFilteredMsg();
  }

  /**
   * Get current message index
   * @return index
   */
  public int getCurrentMsgIndex() {
    return index;
  }

  /**
   * Get Filtered message. This also check applied filter.
   * @return Message
   */
  private Message getFilteredMsg() {
    Message msg = list.get(index);
    if (isFiltered(msg.getType())) {
      return FILTERED_MESSAGE;
    }
    return msg;
  }
  /**
   * Get message with current index
   * @return Message, never null
   */
  public Message getMsg() {
    if (list.size() == 0) {
      return NO_MESSAGE;
    }
    return getFilteredMsg();
  }

  /**
   * Get message with current index ignoring all filters.
   * @return Message, never null
   */
  public Message getMsgIgnoreFilter() {
    if (list.size() == 0) {
      return NO_MESSAGE;
    }
    return list.get(index);
  }

  /**
   * Get maximum number of messages
   * @return Maximum number of messages
   */
  public int getMaxMsg() {
    if (list.size() > 0) {
      return list.size();
    }
    return 1;
  }

  /**
   * Add News message as a first if not already in the list.
   */
  public void addNewsMessage() {
    boolean newsAlreadyInList = false;
    for (Message msg : list) {
      if (msg.getType() == MessageType.NEWS) {
        newsAlreadyInList = true;
        break;
      }
    }
    if (!newsAlreadyInList) {
      Message msg = new Message(MessageType.NEWS,
          "GBNC has made news. Review it by clicking focus button.",
          Icons.getIconByName(Icons.ICON_NEWS));
      addFirstMessage(msg);
    }
  }

  /**
   * Is apply filter enabled?
   * @return boolean
   */
  public boolean isApplyFilter() {
    return applyFilter;
  }

  /**
   * Set apply filter.
   * @param applyFilter Boolean
   */
  public void setApplyFilter(final boolean applyFilter) {
    this.applyFilter = applyFilter;
  }

  @Override
  public String toString() {
    return "MessageList " + (index + 1) + " / " + list.size();
  }

}
