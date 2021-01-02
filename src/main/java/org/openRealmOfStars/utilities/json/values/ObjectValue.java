package org.openRealmOfStars.utilities.json.values;

import java.util.ArrayList;

import org.openRealmOfStars.utilities.json.JsonStream;

/**
*
* Open Realm of Stars game project
* Copyright (C) 2020 Tuomo Untinen
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
* Object Value for Json
*
*/
public class ObjectValue implements JsonValue {

  /**
   * Array of members
   */
  private ArrayList<Member> arrayMember;

  /**
   * Constructor for Object value. No members.
   * This can be used when generating new JSON structure.
   */
  public ObjectValue() {
    arrayMember = new ArrayList<>();
  }

  /**
   * Get array of members.
   * @return Array list of members.
   */
  public ArrayList<Member> getMembers() {
    return arrayMember;
  }
  @Override
  public String getValueAsString() {
    StringBuilder sb = new StringBuilder();
    sb.append(JsonStream.CH_BEGIN_OBJECT);
    for (int i = 0; i < arrayMember.size(); i++) {
      Member value = arrayMember.get(i);
      sb.append(value.getValueAsString());
      if (i < arrayMember.size() - 1) {
        sb.append(JsonStream.CH_VALUE_SEPARATOR);
      }
    }
    sb.append(JsonStream.CH_END_OBJECT);
    return sb.toString();
  }

  /**
   * Find First Member with matching name
   * @param memberName Member name to match
   * @return Member or null if not found.
   */
  public Member findFirst(final String memberName) {
    for (Member member : arrayMember) {
      if (member.getName().equals(memberName)) {
        return member;
      }
      if (member.getValue().getType() == ValueType.OBJECT) {
        ObjectValue next = (ObjectValue) member.getValue();
        Member result = next.findFirst(memberName);
        if (result != null) {
          return result;
        }
      }
      if (member.getValue().getType() == ValueType.ARRAY) {
        ArrayValue array = (ArrayValue) member.getValue();
        for (JsonValue arrayValue : array.getArray()) {
          if (arrayValue.getType() == ValueType.OBJECT) {
            ObjectValue next = (ObjectValue) arrayValue;
            Member result = next.findFirst(memberName);
            if (result != null) {
              return result;
            }
          }
        }
      }
    }
    return null;
  }
  @Override
  public ValueType getType() {
    return ValueType.OBJECT;
  }

  /**
   * Add String member to Object value.
   * If value is null then null is added.
   * @param name Member name
   * @param value String value
   */
  public void addStringMember(final String name, final String value) {
    Member member = new Member(name);
    if (value != null) {
      member.setValue(new StringValue(value));
    } else {
      member.setValue(new NullValue());
    }
    arrayMember.add(member);
  }

  /**
   * Add Integer member to Object value.
   * @param name Member name
   * @param value integer value
   */
  public void addIntegerMember(final String name, final int value) {
    Member member = new Member(name);
    member.setValue(new NumberValue(String.valueOf(value)));
    arrayMember.add(member);
  }

  /**
   * Add Number member to Object value.
   * @param name Member name
   * @param value Number value
   */
  public void addNumberMember(final String name, final String value) {
    Member member = new Member(name);
    member.setValue(new NumberValue(value));
    arrayMember.add(member);
  }

  /**
   * Add boolean member to Object value.
   * @param name Member name
   * @param value Boolean value
   */
  public void addBooleanMember(final String name, final Boolean value) {
    Member member = new Member(name);
    member.setValue(new BooleanValue(value));
    arrayMember.add(member);
  }

}
