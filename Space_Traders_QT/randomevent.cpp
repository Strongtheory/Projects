#include "randomevent.h"
#include <QSharedData>

class RandomEventData : public QSharedData {
public:
    RandomEvent::EventType eventType;
};

RandomEvent::RandomEvent() : data(new RandomEventData)
{
}

RandomEvent::RandomEvent(const RandomEvent &rhs) : data(rhs.data)
{
}

RandomEvent &RandomEvent::operator=(const RandomEvent &rhs)
{
    if (this != &rhs)
        data.operator=(rhs.data);
    return *this;
}

RandomEvent::~RandomEvent()
{
}
