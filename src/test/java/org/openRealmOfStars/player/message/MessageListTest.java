package org.openRealmOfStars.player.message;

import static org.junit.Assert.*;


import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mockito;

/**
 * 
 * Open Realm of Stars game project
 * Copyright (C) 2016  Tuomo Untinen
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
 * 
 * 
 * Test for MessageList class
 */
public class MessageListTest {

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testMessageList() {
    Message msg1 = Mockito.mock(Message.class);
    Message msg2 = Mockito.mock(Message.class);
    MessageList list = new MessageList();
    assertEquals(0, list.getCurrentMsgIndex());
    assertEquals(1, list.getMaxMsg());
    list.addNewMessage(msg1);
    assertEquals(0, list.getCurrentMsgIndex());
    assertEquals(1, list.getMaxMsg());
    list.addNewMessage(msg2);
    assertEquals(0, list.getCurrentMsgIndex());
    assertEquals(2, list.getMaxMsg());
    assertEquals(msg2, list.getNextMessage());
    assertEquals(msg2, list.getMsg());
    assertEquals(msg2, list.getNextMessage());
    assertEquals(msg1, list.getPrevMessage());
    assertEquals(msg1, list.getPrevMessage());
    assertEquals(msg1, list.getMsg());
    assertEquals("MessageList 1 / 2", list.toString());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testMessageList2() {
    Message msg1 = Mockito.mock(Message.class);
    Message msg2 = Mockito.mock(Message.class);
    MessageList list = new MessageList();
    list.addFirstMessage(msg2);
    assertEquals(0, list.getCurrentMsgIndex());
    assertEquals(1, list.getMaxMsg());
    assertEquals(msg2, list.getMsg());
    list.addFirstMessage(msg1);
    assertEquals(0, list.getCurrentMsgIndex());
    assertEquals(2, list.getMaxMsg());
    assertEquals(msg1, list.getMsg());
    assertEquals(msg2, list.getNextMessage());
  }

  @Test
  @Category(org.openRealmOfStars.UnitTest.class)
  public void testUpcomingMessageList() {
    Message msg1 = Mockito.mock(Message.class);
    Message msg2 = Mockito.mock(Message.class);
    MessageList list = new MessageList();
    assertEquals(0, list.getCurrentMsgIndex());
    assertEquals(1, list.getMaxMsg());
    list.addUpcomingMessage(msg1);
    assertEquals(0, list.getCurrentMsgIndex());
    assertEquals(1, list.getMaxMsg());
    list.addUpcomingMessage(msg2);
    assertEquals(0, list.getCurrentMsgIndex());
    assertEquals(1, list.getMaxMsg());
    list.clearMessages();
    assertEquals(0, list.getCurrentMsgIndex());
    assertEquals(2, list.getMaxMsg());
    assertEquals(msg2, list.getNextMessage());
    assertEquals(msg2, list.getMsg());
    assertEquals(msg2, list.getNextMessage());
    assertEquals(msg1, list.getPrevMessage());
    assertEquals(msg1, list.getPrevMessage());
    assertEquals(msg1, list.getMsg());
    assertEquals("MessageList 1 / 2", list.toString());
  }


}
