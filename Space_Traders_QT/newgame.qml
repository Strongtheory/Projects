import QtQuick 2.2
import QtQuick.Controls 1.1

Image {
    id: image1
    source: "qrc:///2015-Star-Citizen.jpg"
    fillMode: Image.PreserveAspectCrop
    anchors.fill: parent
    z: -1

    Label {
        id: label1
        text: qsTr("Name")
        color: "orange"
        anchors.verticalCenter: textField1.verticalCenter
        anchors.left: parent.left
        anchors.leftMargin: 8
    }

    TextField {
        id: textField1
        objectName: "textField1"
        anchors.left: label1.right
        anchors.leftMargin: 6
        anchors.top: parent.top
        anchors.topMargin: 8
        anchors.right: parent.right
        anchors.rightMargin: 8
        placeholderText: qsTr("Name")
    }

    Label {
        id: label3
        text: qsTr("Skill Points Remaining: ")
        color: "orange"
        anchors.bottom: rectangle1.top
        anchors.bottomMargin: 6
        anchors.left: parent.left
        anchors.leftMargin: 8
    }

    Label {
        id: label4
        objectName: "availableSkillPoints"
        color: "orange"
        anchors.left: label3.right
        anchors.leftMargin: 6
        anchors.verticalCenter: label3.verticalCenter
        text: "0"
    }

    Rectangle {
        id: rectangle1
        color: "#7f000000"
        anchors.verticalCenter: parent.verticalCenter
        anchors.right: parent.right
        anchors.rightMargin: 8
        anchors.left: parent.left
        anchors.leftMargin: 8
        height: pilotSkill.height + fighterSkill.height + engineerSkill.height + traderSkill.height + investorSkill.height + 48

        Skill {
            id: pilotSkill
            objectName: "pilotSkill"
            skillName: "Pilot"
            anchors.top: parent.top
            anchors.topMargin: 8
            anchors.left: parent.left
            anchors.leftMargin: 8
        }

        Skill {
            id: fighterSkill
            objectName: "fighterSkill"
            skillName: "Fighter"
            anchors.top: pilotSkill.bottom
            anchors.topMargin: 8
            anchors.left: parent.left
            anchors.leftMargin: 8
        }

        Skill {
            id: engineerSkill
            objectName: "engineerSkill"
            skillName: "Engineer"
            anchors.top: fighterSkill.bottom
            anchors.topMargin: 8
            anchors.left: parent.left
            anchors.leftMargin: 8
        }

        Skill {
            id: traderSkill
            objectName: "traderSkill"
            skillName: "Trader"
            anchors.top: engineerSkill.bottom
            anchors.topMargin: 8
            anchors.left: parent.left
            anchors.leftMargin: 8
        }

        Skill {
            id: investorSkill
            objectName: "investorSkill"
            skillName: "Investor"
            anchors.top: traderSkill.bottom
            anchors.topMargin: 8
            anchors.left: parent.left
            anchors.leftMargin: 8
        }
    }

    Button {
        id: button3
        text: qsTr("Cancel")
        anchors.bottom: parent.bottom
        anchors.bottomMargin: 8
        anchors.left: parent.left
        anchors.leftMargin: 8
        onClicked: mainController.showHomeScreen()
    }

    Button {
        id: button4
        text: qsTr("Create")
        anchors.right: parent.right
        anchors.rightMargin: 8
        anchors.bottom: parent.bottom
        anchors.bottomMargin: 8
        onClicked: {
            newGame.createPlayer();
            mainController.showNavigationPage();
        }
    }

}
