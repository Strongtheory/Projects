#include "newgame.h"
#include "player.h"
#include "maincontroller.h"

#include <QVariant>

NewGame::NewGame(QObject *rootObject, QObject *parent) :
    QObject(parent),
    rootObject(rootObject),
    availableSkillPoints(8)
{

}

void NewGame::setRootObject(QObject *rootObject) {
    this->rootObject = rootObject;
}

void NewGame::showNewGamePage() {
    Q_ASSERT(rootObject);
    rootObject->findChild<QObject*>("pageLoader")->setProperty("source", QStringLiteral("qrc:///newgame.qml"));
    rootObject->findChild<QObject*>("availableSkillPoints")->setProperty("text", availableSkillPoints);
}

void NewGame::incrementSkill(QObject *skillRectangle) {
    int skillLevel = skillRectangle->property("skillLevel").toInt();
    if (availableSkillPoints == 0) {
        return;
    }
    if (skillLevel == 1) {
        skillRectangle->setProperty("isDecrementable", true);
    }
    skillRectangle->setProperty("skillLevel", skillLevel + 1);
    --availableSkillPoints;
    rootObject->findChild<QObject*>("availableSkillPoints")->setProperty("text", availableSkillPoints);
}

void NewGame::decrementSkill(QObject *skillRectangle) {
    int skillLevel = skillRectangle->property("skillLevel").toInt();
    skillRectangle->setProperty("skillLevel", skillLevel - 1);
    if (skillLevel - 1 == 1) {
        skillRectangle->setProperty("isDecrementable", false);
    }
    ++availableSkillPoints;
    rootObject->findChild<QObject*>("availableSkillPoints")->setProperty("text", availableSkillPoints);
}

void NewGame::createPlayer() {
    Player player = Player::getInstance();
    player.setName(rootObject->findChild<QObject*>("textField1")->property("text").toString());
    player.setPilotSkill(rootObject->findChild<QObject*>("pilotSkill")->property("skillLevel").toInt());
    player.setFighterSkill(rootObject->findChild<QObject*>("fighterSkill")->property("skillLevel").toInt());
    player.setEngineerSkill(rootObject->findChild<QObject*>("engineerSkill")->property("skillLevel").toInt());
    player.setTraderSkill(rootObject->findChild<QObject*>("traderSkill")->property("skillLevel").toInt());
    player.setInvestorSkill(rootObject->findChild<QObject*>("investorSkill")->property("skillLevel").toInt());

    static_cast<MainController*>(parent())->saveData();
}
