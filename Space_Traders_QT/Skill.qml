import QtQuick 2.2
import QtQuick.Controls 1.1

Item {
    id: skillRectangle
    property bool isIncrementable: true
    property bool isDecrementable: false
    property int skillLevel: 1
    property string skillName: ""
    anchors.right: parent.right
    anchors.rightMargin: 8
    anchors.left: parent.left
    anchors.leftMargin: 8
    implicitHeight: Math.max(label1.height, button1.height, label2.height, button2.height) + 4

    Label {
        id: label1
        text: skillName
        color: "orange"
        anchors.verticalCenter: button1.verticalCenter
        anchors.left: parent.left
        anchors.leftMargin: 8
    }

    Button {
        id: button1
        width: 40
        text: "-"
        enabled: isDecrementable
        anchors.top: parent.top
        anchors.topMargin: 4
        anchors.left: label1.right
        anchors.leftMargin: 6
        onClicked: newGame.decrementSkill(skillRectangle)
    }

    Label {
        id: label2
        color: "orange"
        anchors.verticalCenter: button1.verticalCenter
        anchors.left: button1.right
        anchors.leftMargin: 6
        text: skillLevel
    }

    Button {
        id: button2
        width: 40
        text: "+"
        enabled: isIncrementable
        anchors.verticalCenter: button1.verticalCenter
        anchors.left: label2.right
        anchors.leftMargin: 6
        onClicked: newGame.incrementSkill(skillRectangle)
    }
}
